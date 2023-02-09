package com.xcx.security.manager;


import com.baomidou.mybatisplus.core.toolkit.AES;
import com.xcx.security.bo.TokenInfoBO;
import com.xcx.security.bo.UserInfoInTokenBO;
import com.xcx.security.common.ResponseEnum;
import com.xcx.security.common.exception.RbacException;
import com.xcx.security.constant.CacheNames;
import com.xcx.security.constant.SysType;
import com.xcx.security.util.IdUtil;
import com.xcx.security.util.PrincipalUtil;
import com.xcx.security.vo.TokenInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.xcx.security.util.DateUtil.*;

/**
 * @author xcx
 * @date 2023/2/7 17:02
 */
@Component
public class TokenStore {
    private static final Logger logger = LoggerFactory.getLogger(TokenStore.class);
    /**
     * 用于aes签名的key，16位
     */
    @Value("${auth.token.signKey}")
    public String tokenSignKey;

    private final RedisTemplate<Object, Object> redisTemplate;

    private final RedisSerializer<Object> redisSerializer;

    private final StringRedisTemplate stringRedisTemplate;

    public TokenStore(RedisTemplate<Object, Object> redisTemplate, RedisSerializer<Object> redisSerializer,
                      StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = redisTemplate;
        this.redisSerializer = redisSerializer;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 将用户的部分信息存储在token中，并返回token信息
     *
     * @param userInfoInToken 用户在token中的信息
     * @return token信息
     */
    public TokenInfoBO storeAccessToken(UserInfoInTokenBO userInfoInToken) {
        TokenInfoBO tokenInfoBO = new TokenInfoBO();
        String accessToken = IdUtil.simpleUUID();
        String refreshToken = IdUtil.simpleUUID();

        tokenInfoBO.setUserInfoInToken(userInfoInToken);
        tokenInfoBO.setExpiresIn(getExpiresIn(userInfoInToken.getSysType()));

        String uidToAccessKeyStr = getUidToAccessKey(getApprovalKey(userInfoInToken));
        String accessKeyStr = getAccessKey(accessToken);
        String refreshToAccessKeyStr = getRefreshToAccessKey(refreshToken);

        // 一个用户会登录很多次，每次登陆的token都会存在 uid_to_access里面
        // 但是每次保存都会更新这个key的时间，而key里面的token有可能会过期，过期就要移除掉
        List<String> existsAccessTokens = new ArrayList<>();
        // 新的token数据
        existsAccessTokens.add(accessToken + CacheNames.UNION_KEY + refreshToken);

        Long size = redisTemplate.opsForSet().size(uidToAccessKeyStr);
        if (size != null && size != 0) {
            List<String> tokenInfoBoList = stringRedisTemplate.opsForSet().pop(uidToAccessKeyStr, size);
            if (tokenInfoBoList != null) {
                for (String accessTokenWithRefreshToken : tokenInfoBoList) {
                    String[] accessTokenWithRefreshTokenArr = accessTokenWithRefreshToken.split(CacheNames.UNION_KEY);
                    String accessTokenData = accessTokenWithRefreshTokenArr[0];
                    if (stringRedisTemplate.hasKey(getAccessKey(accessTokenData))) {
                        existsAccessTokens.add(accessTokenWithRefreshToken);
                    }
                }
            }
        }

        redisTemplate.executePipelined((RedisCallback<Object>) connection -> {

            long expiresIn = tokenInfoBO.getExpiresIn();

            byte[] uidKey = uidToAccessKeyStr.getBytes(StandardCharsets.UTF_8);
            byte[] refreshKey = refreshToAccessKeyStr.getBytes(StandardCharsets.UTF_8);
            byte[] accessKey = accessKeyStr.getBytes(StandardCharsets.UTF_8);

            for (String existsAccessToken : existsAccessTokens) {
                connection.sAdd(uidKey, existsAccessToken.getBytes(StandardCharsets.UTF_8));
            }

            // 通过uid + sysType 保存access_token，当需要禁用用户的时候，可以根据uid + sysType 禁用用户
            connection.expire(uidKey, expiresIn);

            // 通过refresh_token获取用户的access_token从而刷新token
            connection.setEx(refreshKey, expiresIn, accessToken.getBytes(StandardCharsets.UTF_8));

            // 通过access_token保存用户的id，uid
            connection.setEx(accessKey, HOUR_IN_SECOND, Objects.requireNonNull(redisSerializer.serialize(userInfoInToken)));

            return null;
        });

        // 返回给前端是加密的token
//        tokenInfoBO.setAccessToken(encryptToken(accessToken, userInfoInToken.getSysType()));
//        tokenInfoBO.setRefreshToken(encryptToken(refreshToken, userInfoInToken.getSysType()));
        tokenInfoBO.setAccessToken(accessToken);
        tokenInfoBO.setRefreshToken(refreshToken);

        return tokenInfoBO;
    }

    private int getExpiresIn(int sysType) {

        // 普通用户token过期时间 一周
        if (Objects.equals(sysType, SysType.ORDINARY)) {
            return WEEK_IN_SECOND;
        }
        // 系统管理员的token过期时间 一天
        if (Objects.equals(sysType, SysType.PLATFORM)) {
            return DAY_IN_SECOND;
        }
        return HOUR_IN_SECOND;
    }

    /**
     * 根据accessToken 获取用户信息
     *
     * @param accessToken accessToken
     * @param needDecrypt 是否需要解密
     * @return 用户信息
     */
    public UserInfoInTokenBO getUserInfoByAccessToken(String accessToken, boolean needDecrypt) {
        if (StringUtils.isBlank(accessToken)) {
            throw new RbacException("accessToken is blank");
        }
        String realAccessToken;
        if (needDecrypt) {
            realAccessToken = decryptToken(accessToken, true);
        } else {
            realAccessToken = accessToken;
        }
        UserInfoInTokenBO userInfoInTokenBO = (UserInfoInTokenBO) redisTemplate.opsForValue()
                .get(getAccessKey(realAccessToken));

        if (userInfoInTokenBO == null) {
            throw new RbacException("accessToken 已过期");
        }
        return userInfoInTokenBO;
    }

    /**
     * 刷新token，并返回新的token
     *
     * @param refreshToken
     */
    public TokenInfoVO refreshToken(String refreshToken) {
        if (StringUtils.isBlank(refreshToken)) {
            throw new RbacException("refreshToken is blank");
        }
//        String realRefreshToken = decryptToken(refreshToken, false);
        String realRefreshToken = refreshToken;
        String accessToken = stringRedisTemplate.opsForValue().get(getRefreshToAccessKey(realRefreshToken));

        if (StringUtils.isBlank(accessToken)) {
            throw new RbacException("refreshToken 已过期");
        }

        UserInfoInTokenBO userInfoInTokenBO = getUserInfoByAccessToken(accessToken,
                false);
        //TODO 逻辑待调整

        // 删除旧的refresh_token
        stringRedisTemplate.delete(getRefreshToAccessKey(realRefreshToken));
        // 删除旧的access_token
        stringRedisTemplate.delete(getAccessKey(accessToken));
        // 保存一份新的token
        TokenInfoBO tokenInfoBO = storeAccessToken(userInfoInTokenBO);
        TokenInfoVO tokenInfoVO = new TokenInfoVO();
        tokenInfoVO.setAccessToken(tokenInfoBO.getAccessToken());
        tokenInfoVO.setRefreshToken(tokenInfoBO.getRefreshToken());
        return tokenInfoVO;
    }

    /**
     * 删除全部的token
     */
    public void deleteAllToken(String appId, Long uid) {
        String uidKey = getUidToAccessKey(getApprovalKey(appId, uid));
        Long size = redisTemplate.opsForSet().size(uidKey);
        if (size == null || size == 0) {
            return;
        }
        List<String> tokenInfoBoList = stringRedisTemplate.opsForSet().pop(uidKey, size);

        if (CollectionUtils.isEmpty(tokenInfoBoList)) {
            return;
        }

        for (String accessTokenWithRefreshToken : tokenInfoBoList) {
            String[] accessTokenWithRefreshTokenArr = accessTokenWithRefreshToken.split(CacheNames.UNION_KEY);
            String accessToken = accessTokenWithRefreshTokenArr[0];
            String refreshToken = accessTokenWithRefreshTokenArr[1];
            redisTemplate.delete(getRefreshToAccessKey(refreshToken));
            redisTemplate.delete(getAccessKey(accessToken));
        }
        redisTemplate.delete(uidKey);

    }

    private static String getApprovalKey(UserInfoInTokenBO userInfoInToken) {
        return getApprovalKey(userInfoInToken.getSysType().toString(), userInfoInToken.getUid());
    }

    private static String getApprovalKey(String appId, Long uid) {
        return uid == null ? appId : appId + CacheNames.UNION_KEY + uid;
    }

    private String encryptToken(String token, Integer sysType) {

        return AES.encrypt(token + System.currentTimeMillis() + sysType, tokenSignKey);

//        AES aes = new AES(tokenSignKey.getBytes(StandardCharsets.UTF_8));
//        return aes.encryptBase64(accessToken + System.currentTimeMillis() + sysType);
    }

    private String decryptToken(String data, boolean isAccess) {
//        AES aes = new AES(tokenSignKey.getBytes(StandardCharsets.UTF_8));
        String decryptStr;
        String decryptToken;
        try {
//            decryptStr = aes.decryptStr(data);
            decryptStr = AES.decrypt(data, tokenSignKey);
            decryptToken = decryptStr.substring(0, 32);
            // 创建token的时间，token使用时效性，防止攻击者通过一堆的尝试找到aes的密码，虽然aes是目前几乎最好的加密算法
            long createTokenTime = Long.parseLong(decryptStr.substring(32, 45));
            // 系统类型
            int sysType = Integer.parseInt(decryptStr.substring(45));
            // token的过期时间
            int expiresIn = isAccess ? HOUR_IN_SECOND : getExpiresIn(sysType);
            long second = 1000L;
            if (System.currentTimeMillis() - createTokenTime > expiresIn * second) {
                throw new RbacException("token 格式有误");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RbacException("token 格式有误");
        }

        // 防止解密后的token是脚本，从而对redis进行攻击，uuid只能是数字和小写字母
        if (!PrincipalUtil.isSimpleChar(decryptToken)) {
            throw new RbacException("token 格式有误");
        }
        return decryptToken;
    }

    public String getAccessKey(String accessToken) {
        return CacheNames.ACCESS + accessToken;
    }

    public String getUidToAccessKey(String approvalKey) {
        return CacheNames.UID_TO_ACCESS + approvalKey;
    }

    public String getRefreshToAccessKey(String refreshToken) {
        return CacheNames.REFRESH_TO_ACCESS + refreshToken;
    }

    public TokenInfoVO storeAndGetVo(UserInfoInTokenBO userInfoInToken) {
        TokenInfoBO tokenInfoBO = storeAccessToken(userInfoInToken);

        TokenInfoVO tokenInfoVO = new TokenInfoVO();
        tokenInfoVO.setAccessToken(tokenInfoBO.getAccessToken());
        tokenInfoVO.setRefreshToken(tokenInfoBO.getRefreshToken());
        return tokenInfoVO;
    }

    public void updateUserInfoByUidAndAppId(Long uid, String appId, UserInfoInTokenBO userInfoInTokenBO) {
        if (userInfoInTokenBO == null) {
            return;
        }
        String uidKey = getUidToAccessKey(getApprovalKey(appId, uid));
        Set<String> tokenInfoBoList = stringRedisTemplate.opsForSet().members(uidKey);
        if (tokenInfoBoList == null || tokenInfoBoList.size() == 0) {
            throw new RbacException(ResponseEnum.UNAUTHORIZED);
        }
        for (String accessTokenWithRefreshToken : tokenInfoBoList) {
            String[] accessTokenWithRefreshTokenArr = accessTokenWithRefreshToken.split(CacheNames.UNION_KEY);
            String accessKey = this.getAccessKey(accessTokenWithRefreshTokenArr[0]);
            UserInfoInTokenBO oldUserInfoInTokenBO = (UserInfoInTokenBO) redisTemplate.opsForValue().get(accessKey);
            if (oldUserInfoInTokenBO == null) {
                continue;
            }
            BeanUtils.copyProperties(userInfoInTokenBO, oldUserInfoInTokenBO);
            redisTemplate.opsForValue().set(accessKey, Objects.requireNonNull(userInfoInTokenBO), HOUR_IN_SECOND, TimeUnit.SECONDS);
        }
    }
}
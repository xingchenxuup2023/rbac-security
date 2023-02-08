package com.xcx.security.service.rbac.impl;

import com.xcx.security.bo.AuthAccountInVerifyBO;
import com.xcx.security.bo.UserInfoInTokenBO;
import com.xcx.security.common.Result;
import com.xcx.security.common.exception.RbacException;
import com.xcx.security.constant.AuthAccountStatusEnum;
import com.xcx.security.constant.InputUserNameEnum;
import com.xcx.security.model.rbac.AuthAccount;
import com.xcx.security.mapper.rbac.AuthAccountMapper;
import com.xcx.security.service.rbac.AuthAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xcx.security.util.PrincipalUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * <p>
 * 统一账户信息 服务实现类
 * </p>
 *
 * @author 邢晨旭
 * @since 2023-02-01
 */
@Service
public class AuthAccountServiceImpl extends ServiceImpl<AuthAccountMapper, AuthAccount> implements AuthAccountService {

    @Resource
    private AuthAccountMapper authAccountMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    private static String userNotFoundEncodedPassword;

    public static final String USER_NOT_FOUND_SECRET = "USER_NOT_FOUND_SECRET";

    @Override
    public UserInfoInTokenBO getUserInfoInTokenByInputUserNameAndPassword(String username, String password, Integer sysType) {
        if (StringUtils.isBlank(username)) {
            throw new RbacException("用户名不能为空");
        }
        if (StringUtils.isBlank(password)) {
            throw new RbacException("密码不能为空");
        }

        InputUserNameEnum inputUserNameEnum = null;

        // 用户名
        if (PrincipalUtil.isUserName(username)) {
            inputUserNameEnum = InputUserNameEnum.USERNAME;
        }

        if (inputUserNameEnum == null) {
            throw new RbacException("请输入正确的用户名");
        }

        AuthAccountInVerifyBO authAccountInVerifyBO = authAccountMapper
                .getAuthAccountInVerifyByInputUserName(inputUserNameEnum.value(), username, sysType);

        if (authAccountInVerifyBO == null) {
            prepareTimingAttackProtection();
            // 再次进行运算，防止计时攻击
            // 计时攻击（Timing
            // attack），通过设备运算的用时来推断出所使用的运算操作，或者通过对比运算的时间推定数据位于哪个存储设备，或者利用通信的时间差进行数据窃取。
            mitigateAgainstTimingAttack(password);
            throw new RbacException("用户名或密码不正确");
        }

        if (Objects.equals(authAccountInVerifyBO.getStatus(), AuthAccountStatusEnum.DISABLE.value())) {
            throw new RbacException("用户已禁用，请联系客服");
        }

        if (!passwordEncoder.matches(password, authAccountInVerifyBO.getPassword())) {
            throw new RbacException("用户名或密码不正确");
        }

        return authAccountInVerifyBO;
    }


    private void prepareTimingAttackProtection() {
        if (userNotFoundEncodedPassword == null) {
            userNotFoundEncodedPassword = this.passwordEncoder.encode(USER_NOT_FOUND_SECRET);
        }
    }

    /**
     * 防止计时攻击
     */
    private void mitigateAgainstTimingAttack(String presentedPassword) {
        if (presentedPassword != null) {
            this.passwordEncoder.matches(presentedPassword, userNotFoundEncodedPassword);
        }
    }

    public static void main(String[] args) {
        //$2a$10$vTwt/TSeibtDi5M1QHawjeP5agDrOrEtPx1WlUNkM1koxxLxYdwCa
        System.out.println(new BCryptPasswordEncoder().encode("111111"));
    }
}

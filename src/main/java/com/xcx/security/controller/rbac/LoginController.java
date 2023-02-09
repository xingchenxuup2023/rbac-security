package com.xcx.security.controller.rbac;

import com.xcx.security.bo.UserInfoInTokenBO;
import com.xcx.security.common.Result;
import com.xcx.security.context.AuthUserContext;
import com.xcx.security.dto.AuthenticationDTO;
import com.xcx.security.manager.TokenStore;
import com.xcx.security.service.rbac.AuthAccountService;
import com.xcx.security.service.rbac.MenuPermissionService;
import com.xcx.security.vo.TokenInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author xcx
 * @date 2023/2/8 10:53
 */
@RestController
@Api(tags = "登录")
@CrossOrigin
public class LoginController {
    @Resource
    private TokenStore tokenStore;

    @Resource
    private AuthAccountService authAccountService;

    @Resource
    private MenuPermissionService menuPermissionService;


    @PostMapping("/ua/login")
    @ApiOperation(value = "账号密码", notes = "通过账号登录，还要携带用户的类型，也就是用户所在的系统")
    public Result<TokenInfoVO> login(@RequestBody AuthenticationDTO authenticationDTO) {
        UserInfoInTokenBO data = authAccountService
                .getUserInfoInTokenByInputUserNameAndPassword(authenticationDTO.getUsername(),
                        authenticationDTO.getCredentials(), authenticationDTO.getSysType());
        // 将以前的权限清理了,以免权限有缓存
        menuPermissionService.clearUserPermissionsCache(data.getUserId(), data.getSysType());
        // 保存token，返回token数据给前端，这里是最重要的
        return Result.ok(tokenStore.storeAndGetVo(data));
    }

    @PostMapping("/login_out")
    @ApiOperation(value = "退出登陆", notes = "点击退出登陆，清除token，清除菜单缓存")
    public Result<TokenInfoVO> loginOut() {
        UserInfoInTokenBO userInfoInToken = AuthUserContext.get();
        // 将以前的权限清理了,以免权限有缓存
        menuPermissionService.clearUserPermissionsCache(userInfoInToken.getUserId(), userInfoInToken.getSysType());
        // 删除该用户在该系统的token
        tokenStore.deleteAllToken(userInfoInToken.getSysType().toString(), userInfoInToken.getUid());
        return Result.ok();
    }

    @GetMapping("/token/refresh")
    @ApiOperation(value = "刷新token", notes = "刷新token")
    public Result<TokenInfoVO> refreshAccessToken(@RequestParam("refreshToken") String refreshToken) {
        return Result.ok(tokenStore.refreshToken(refreshToken));
    }
}

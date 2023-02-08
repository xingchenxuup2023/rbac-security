package com.xcx.security.service.rbac;

import com.xcx.security.bo.UserInfoInTokenBO;
import com.xcx.security.common.Result;
import com.xcx.security.model.rbac.AuthAccount;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 统一账户信息 服务类
 * </p>
 *
 * @author 邢晨旭
 * @since 2023-02-01
 */
public interface AuthAccountService extends IService<AuthAccount> {

    UserInfoInTokenBO getUserInfoInTokenByInputUserNameAndPassword(String username, String password, Integer sysType);
}

package com.xcx.security.context;

import com.xcx.security.bo.UserInfoInTokenBO;

/**
 * @author 邢晨旭
 * @date 2023/1/30
 * @description 请求用户上下文
 */
public class AuthUserContext {
    /**
     * The request holder.
     */
    private static final ThreadLocal<UserInfoInTokenBO> USER_INFO_IN_TOKEN_HOLDER = new ThreadLocal<>();

    public static UserInfoInTokenBO get() {
        return USER_INFO_IN_TOKEN_HOLDER.get();
    }


    public static UserInfoInTokenBO forceGet() {
        return USER_INFO_IN_TOKEN_HOLDER.get();
    }

    public static void set(UserInfoInTokenBO userInfoInTokenBo) {
        USER_INFO_IN_TOKEN_HOLDER.set(userInfoInTokenBo);
    }

    public static void clean() {
        if (USER_INFO_IN_TOKEN_HOLDER.get() != null) {
            USER_INFO_IN_TOKEN_HOLDER.remove();
        }
    }
}

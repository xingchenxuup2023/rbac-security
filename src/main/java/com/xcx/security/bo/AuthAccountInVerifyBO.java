package com.xcx.security.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author xcx
 * @date 2023/2/8 11:04
 */
@Getter
@Setter
@ToString
public class AuthAccountInVerifyBO extends UserInfoInTokenBO {

    private String password;

    private Integer status;

    public String getPassword() {
        return password;
    }
}

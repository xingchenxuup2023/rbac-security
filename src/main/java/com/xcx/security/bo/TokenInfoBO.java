package com.xcx.security.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * token信息，该信息存在redis中
 *
 * @author xcx
 * @date 2023/2/7 17:05
 */
@Getter
@Setter
@ToString
public class TokenInfoBO {
    /**
     * 保存在token信息里面的用户信息
     */
    private UserInfoInTokenBO userInfoInToken;

    private String accessToken;

    private String refreshToken;

    /**
     * 在多少秒后过期
     */
    private Integer expiresIn;


}

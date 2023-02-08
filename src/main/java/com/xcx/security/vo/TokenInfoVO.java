package com.xcx.security.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * token信息，该信息用户返回给前端，前端请求携带accessToken进行用户校验
 *
 * @author xcx
 * @date 2023/2/7 17:21
 */
@Getter
@Setter
@ToString
public class TokenInfoVO {
    @ApiModelProperty("accessToken")
    private String accessToken;

    @ApiModelProperty("refreshToken")
    private String refreshToken;

}

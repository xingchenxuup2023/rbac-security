package com.xcx.security.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 登录时传递账号密码
 *
 * @author xcx
 * @date 2023/2/8 10:56
 */
@Getter
@Setter
@ToString
public class AuthenticationDTO {
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", required = true)
    protected String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "一般用作密码", required = true)
    protected String credentials;

    /**
     * @see com.xcx.security.constant.SysType
     */
    @ApiModelProperty(value = "系统类型 0.普通用户系统 1.平台", required = true)
    protected Integer sysType;
}

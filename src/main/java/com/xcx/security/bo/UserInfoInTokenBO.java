package com.xcx.security.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 邢晨旭
 * @date 2023/1/30
 */
@Getter
@Setter
@ToString
public class UserInfoInTokenBO {
    /**
     * 用户在自己系统的用户id
     */
    private Long userId;

    /**
     * 全局唯一的id,
     */
    private Long uid;


    /**
     * 系统类型
     *
     * @see com.xcx.security.constant.SysType
     */
    private Integer sysType;

    /**
     * 是否是管理员
     */
    private Integer isAdmin;

}

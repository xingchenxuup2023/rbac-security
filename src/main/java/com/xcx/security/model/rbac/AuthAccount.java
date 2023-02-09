package com.xcx.security.model.rbac;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xcx.security.model.BaseModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 统一账户信息
 * </p>
 *
 * @author 邢晨旭
 * @since 2023-02-01
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("auth_account")
public class AuthAccount extends BaseModel {

    /**
     * 用户全局唯一id
     */
    @TableId(value = "uid", type = IdType.AUTO)
    private Long uid;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 创建ip
     */
    @TableField("create_ip")
    private String createIp;

    /**
     * 状态 1:启用 0:禁用 -1:删除
     */
    @TableField("status")
    private Byte status;

    /**
     * 用户类型 普通用户系统(0)/平台(1、2...)
     */
    @TableField("sys_type")
    private Byte sysType;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 是否是管理员
     */
    @TableField("is_admin")
    private Boolean isAdmin;
}

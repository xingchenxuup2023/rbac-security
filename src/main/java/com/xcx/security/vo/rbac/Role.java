package com.xcx.security.vo.rbac;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xcx.security.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色
 * </p>
 *
 * @author 邢晨旭
 * @since 2023-02-01
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("role")
public class Role extends BaseVo {

    /**
     * 角色id
     */
    @TableId(value = "role_id", type = IdType.AUTO)
    private Long roleId;

    /**
     * 角色名称
     */
    @TableField("role_name")
    private String roleName;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 创建者ID
     */
    @TableField("create_user_id")
    private Long createUserId;

    /**
     * 业务类型（多平台使用）
     */
    @TableField("biz_type")
    private Byte bizType;
}

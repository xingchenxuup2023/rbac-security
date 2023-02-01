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
 * 角色与菜单对应关系
 * </p>
 *
 * @author 邢晨旭
 * @since 2023-02-01
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("role_menu")
public class RoleMenu extends BaseVo {

    /**
     * 关联id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 角色ID
     */
    @TableField("role_id")
    private Long roleId;

    /**
     * 菜单ID
     */
    @TableField("menu_id")
    private Long menuId;

    /**
     * 菜单资源用户id
     */
    @TableField("menu_permission_id")
    private Long menuPermissionId;
}

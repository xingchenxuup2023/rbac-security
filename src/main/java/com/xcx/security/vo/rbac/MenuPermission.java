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
 * 菜单资源
 * </p>
 *
 * @author 邢晨旭
 * @since 2023-02-01
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("menu_permission")
public class MenuPermission extends BaseVo {

    /**
     * 菜单资源用户id
     */
    @TableId(value = "menu_permission_id", type = IdType.AUTO)
    private Long menuPermissionId;

    /**
     * 资源关联菜单
     */
    @TableField("menu_id")
    private Long menuId;

    /**
     * 业务类型（多平台使用）
     */
    @TableField("biz_type")
    private Byte bizType;

    /**
     * 权限对应的编码
     */
    @TableField("permission")
    private String permission;

    /**
     * 资源名称
     */
    @TableField("name")
    private String name;

    /**
     * 资源对应服务器路径
     */
    @TableField("uri")
    private String uri;

    /**
     * 请求方法 1.GET 2.POST 3.PUT 4.DELETE
     */
    @TableField("method")
    private Byte method;
}

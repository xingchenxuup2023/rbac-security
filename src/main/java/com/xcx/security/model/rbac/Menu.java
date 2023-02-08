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
 * 菜单管理
 * </p>
 *
 * @author 邢晨旭
 * @since 2023-02-01
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("menu")
public class Menu extends BaseModel {

    /**
     * 菜单id
     */
    @TableId(value = "menu_id", type = IdType.AUTO)
    private Long menuId;

    /**
     * 父菜单ID，一级菜单为0
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 业务类型（多平台使用）
     */
    @TableField("biz_type")
    private Boolean bizType;

    /**
     * 权限，需要有哪个权限才能访问该菜单
     */
    @TableField("permission")
    private String permission;

    /**
     * 路径 就像uri
     */
    @TableField("path")
    private String path;

    /**
     * 1.'Layout' 为布局，不会跳页面 2.'components-demo/tinymce' 跳转到该页面
     */
    @TableField("component")
    private String component;

    /**
     * 当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
     */
    @TableField("redirect")
    private String redirect;

    /**
     * 一直显示根路由
     */
    @TableField("always_show")
    private Boolean alwaysShow;

    /**
     * 当设置 true 的时候该路由不会在侧边栏出现 如401，login等页面，或者如一些编辑页面/edit/1
     */
    @TableField("hidden")
    private Boolean hidden;

    /**
     * 设定路由的名字，一定要填写不然使用<keep-alive>时会出现各种问题
     */
    @TableField("name")
    private String name;

    /**
     * 设置该路由在侧边栏和面包屑中展示的名字
     */
    @TableField("title")
    private String title;

    /**
     * 设置该路由的图标，支持 svg-class，也支持 el-icon-x element-ui 的 icon
     */
    @TableField("icon")
    private String icon;

    /**
     * 如果设置为true，则不会被 <keep-alive> 缓存(默认 false)
     */
    @TableField("no_cache")
    private Boolean noCache;

    /**
     * 如果设置为false，则不会在breadcrumb面包屑中显示(默认 true)
     */
    @TableField("breadcrumb")
    private Boolean breadcrumb;

    /**
     * 若果设置为true，它则会固定在tags-view中(默认 false)
     */
    @TableField("affix")
    private Boolean affix;

    /**
     * 当路由设置了该属性，则会高亮相对应的侧边栏。
     */
    @TableField("active_menu")
    private String activeMenu;

    /**
     * 排序，越小越靠前
     */
    @TableField("seq")
    private Integer seq;
}

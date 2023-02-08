package com.xcx.security.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author xcx
 * @date 2023/2/7 14:52
 */
@Getter
@Setter
@ToString
public class RouteMetaVO {

    /**
     * 设置该路由在侧边栏和面包屑中展示的名字
     */
    @ApiModelProperty("设置该路由在侧边栏和面包屑中展示的名字")
    private String title;

    /**
     * 设置该路由的图标，支持 svg-class，也支持 el-icon-x element-ui 的 icon
     */
    @ApiModelProperty("设置该路由的图标，支持 svg-class，也支持 el-icon-x element-ui 的 icon")
    private String icon;

    /**
     * 如果设置为true，则不会被 <keep-alive> 缓存(默认 false)
     */
    @ApiModelProperty("如果设置为true，则不会被 <keep-alive> 缓存(默认 false)")
    private Boolean noCache;

    /**
     * 如果设置为false，则不会在breadcrumb面包屑中显示(默认 true)
     */
    @ApiModelProperty("如果设置为false，则不会在breadcrumb面包屑中显示(默认 true)")
    private Boolean breadcrumb;

    /**
     * 若果设置为true，它则会固定在tags-view中(默认 false)
     */
    @ApiModelProperty("若果设置为true，它则会固定在tags-view中(默认 false)")
    private Boolean affix;

    /**
     * 当路由设置了该属性，则会高亮相对应的侧边栏。 这在某些场景非常有用，比如：一个文章的列表页路由为：/article/list
     * 点击文章进入文章详情页，这时候路由为/article/1，但你想在侧边栏高亮文章列表的路由，就可以进行如下设置
     */
    @ApiModelProperty("当路由设置了该属性，则会高亮相对应的侧边栏。")
    private String activeMenu;

    @ApiModelProperty("需要什么权限才能访问该菜单")
    private List<String> roles;


}


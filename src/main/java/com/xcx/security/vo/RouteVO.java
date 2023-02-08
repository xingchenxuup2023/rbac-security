package com.xcx.security.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 邢晨旭
 * @date 2023/2/7
 */
@Getter
@Setter
@ToString
public class RouteVO {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("parentId")
    private Long parentId;

    /**
     * 就像uri
     */
    @ApiModelProperty("路径: 就像uri")
    private String path;

    /**
     * 组件： 'layout/Layout' 为布局，不会跳页面 'views/components-demo/tinymce' 跳转到该页面
     */
    @ApiModelProperty("组件如：1.'Layout' 为布局，不会跳页面 2.'components-demo/tinymce' 跳转到该页面")
    private String component;

    /**
     * 当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
     */
    @ApiModelProperty("当设置 noRedirect 的时候该路由在面包屑导航中不可被点击")
    private String redirect;

    /**
     * 一直显示根路由
     */
    @ApiModelProperty("一直显示根路由")
    private Boolean alwaysShow;

    /**
     * 当设置 true 的时候该路由不会在侧边栏出现 如401，login等页面，或者如一些编辑页面/edit/1
     */
    @ApiModelProperty("当设置 true 的时候该路由不会在侧边栏出现 如401，login等页面，或者如一些编辑页面/edit/1")
    private Boolean hidden;

    /**
     * 设定路由的名字，一定要填写不然使用<keep-alive>时会出现各种问题
     */
    @ApiModelProperty("设定路由的名字，一定要填写不然使用<keep-alive>时会出现各种问题")
    private String name;

    /**
     * 设定路由的名字，一定要填写不然使用<keep-alive>时会出现各种问题
     */
    @ApiModelProperty("排序")
    private Integer seq;

    /**
     * 路由的源信息
     */
    @ApiModelProperty("路由的源信息")
    private RouteMetaVO meta;


}

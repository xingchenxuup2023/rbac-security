package com.xcx.security.controller.rbac;

import com.xcx.security.common.Result;
import com.xcx.security.context.AuthUserContext;
import com.xcx.security.model.rbac.Menu;
import com.xcx.security.service.rbac.MenuService;
import com.xcx.security.vo.RouteMetaVO;
import com.xcx.security.vo.RouteVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 菜单管理 前端控制器
 * </p>
 *
 * @author 邢晨旭
 * @since 2023-02-01
 */
@RestController
@RequestMapping("/menu")
@CrossOrigin
public class MenuController {

    @Resource
    MenuService menuService;

    @GetMapping(value = "/route")
    @ApiOperation(value = "路由菜单", notes = "获取当前登陆用户可用的路由菜单列表")
    public Result<List<RouteVO>> route(Integer sysType) {
//        sysType = Objects.isNull(sysType) ? AuthUserContext.get().getSysType() : sysType;
        List<Menu> menus = menuService.listBySysType(1);

        List<RouteVO> routes = new ArrayList<>(menus.size());

        for (Menu menu : menus) {
            RouteVO route = new RouteVO();
            route.setAlwaysShow(menu.getAlwaysShow());
            route.setComponent(menu.getComponent());
            route.setHidden(menu.getHidden());
            route.setName(menu.getName());
            route.setPath(menu.getPath());
            route.setRedirect(menu.getRedirect());
            route.setId(menu.getMenuId());
            route.setParentId(menu.getParentId());
            route.setSeq(menu.getSeq());

            RouteMetaVO meta = new RouteMetaVO();
            meta.setActiveMenu(menu.getActiveMenu());
            meta.setAffix(menu.getAffix());
            meta.setBreadcrumb(menu.getBreadcrumb());
            meta.setIcon(menu.getIcon());
            meta.setNoCache(menu.getNoCache());
            meta.setTitle(menu.getTitle());
            // 对于前端来说角色就是权限
            meta.setRoles(Collections.singletonList(menu.getPermission()));

            route.setMeta(meta);
            routes.add(route);
        }
        return Result.ok(routes);
    }

}

package com.xcx.security.service.rbac;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xcx.security.model.rbac.MenuPermission;

/**
 * <p>
 * 菜单资源 服务类
 * </p>
 *
 * @author 邢晨旭
 * @since 2023-02-01
 */
public interface MenuPermissionService extends IService<MenuPermission> {

    void clearUserPermissionsCache(Long userId, Integer sysType);

}

package com.xcx.security.service.rbac.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xcx.security.constant.CacheNames;
import com.xcx.security.mapper.rbac.MenuPermissionMapper;
import com.xcx.security.model.rbac.MenuPermission;
import com.xcx.security.service.rbac.MenuPermissionService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜单资源 服务实现类
 * </p>
 *
 * @author 邢晨旭
 * @since 2023-02-01
 */
@Service
public class MenuPermissionServiceImpl extends ServiceImpl<MenuPermissionMapper, MenuPermission> implements MenuPermissionService {
    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = CacheNames.USER_PERMISSIONS_KEY, key = "#sysType + ':' + #userId"),
            @CacheEvict(cacheNames = CacheNames.MENU_ID_LIST_KEY, key = "#userId")
    })
    public void clearUserPermissionsCache(Long userId, Integer sysType) {
    }
}

package com.xcx.security.service.rbac.impl;

import com.xcx.security.model.rbac.RoleMenu;
import com.xcx.security.mapper.rbac.RoleMenuMapper;
import com.xcx.security.service.rbac.RoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色与菜单对应关系 服务实现类
 * </p>
 *
 * @author 邢晨旭
 * @since 2023-02-01
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

}

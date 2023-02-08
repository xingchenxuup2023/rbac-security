package com.xcx.security.service.rbac.impl;

import com.xcx.security.model.rbac.UserRole;
import com.xcx.security.mapper.rbac.UserRoleMapper;
import com.xcx.security.service.rbac.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户与角色对应关系 服务实现类
 * </p>
 *
 * @author 邢晨旭
 * @since 2023-02-01
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}

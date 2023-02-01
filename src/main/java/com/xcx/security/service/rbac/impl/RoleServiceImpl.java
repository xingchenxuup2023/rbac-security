package com.xcx.security.service.rbac.impl;

import com.xcx.security.vo.rbac.Role;
import com.xcx.security.mapper.rbac.RoleMapper;
import com.xcx.security.service.rbac.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author 邢晨旭
 * @since 2023-02-01
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}

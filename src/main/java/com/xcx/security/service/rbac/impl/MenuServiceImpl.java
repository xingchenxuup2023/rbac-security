package com.xcx.security.service.rbac.impl;

import com.xcx.security.model.rbac.Menu;
import com.xcx.security.mapper.rbac.MenuMapper;
import com.xcx.security.service.rbac.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 菜单管理 服务实现类
 * </p>
 *
 * @author 邢晨旭
 * @since 2023-02-01
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Resource
    MenuMapper menuMapper;

    @Override
    public List<Menu> listBySysType(Integer sysType) {
        return menuMapper.listBySysType(sysType);
    }
}

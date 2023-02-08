package com.xcx.security.service.rbac;

import com.xcx.security.model.rbac.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单管理 服务类
 * </p>
 *
 * @author 邢晨旭
 * @since 2023-02-01
 */
public interface MenuService extends IService<Menu> {

    List<Menu> listBySysType(Integer sysType);
}

package com.xcx.security.mapper.rbac;

import com.xcx.security.model.rbac.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单管理 Mapper 接口
 * </p>
 *
 * @author 邢晨旭
 * @since 2023-02-01
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {


    /**
     * 根据系统类型获取该系统的菜单列表
     *
     * @param sysType 系统类型
     * @return 菜单列表
     */
    List<Menu> listBySysType(@Param("sysType") Integer sysType);
}

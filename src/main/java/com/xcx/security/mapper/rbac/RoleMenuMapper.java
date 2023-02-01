package com.xcx.security.mapper.rbac;

import com.xcx.security.vo.rbac.RoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 角色与菜单对应关系 Mapper 接口
 * </p>
 *
 * @author 邢晨旭
 * @since 2023-02-01
 */
@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

}

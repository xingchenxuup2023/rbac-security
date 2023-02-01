package com.xcx.security.mapper.rbac;

import com.xcx.security.vo.rbac.AuthAccount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 统一账户信息 Mapper 接口
 * </p>
 *
 * @author 邢晨旭
 * @since 2023-02-01
 */
@Mapper
public interface AuthAccountMapper extends BaseMapper<AuthAccount> {

}

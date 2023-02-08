package com.xcx.security.mapper.rbac;

import com.xcx.security.bo.AuthAccountInVerifyBO;
import com.xcx.security.model.rbac.AuthAccount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
    /**
     * 根据输入的用户名及用户名类型获取用户信息
     *
     * @param inputUserNameType 输入的用户名类型 1.username 2.mobile 3.email
     * @param inputUserName     输入的用户名
     * @param sysType           系统类型
     * @return 用户在token中信息 + 数据库中的密码
     */
    AuthAccountInVerifyBO getAuthAccountInVerifyByInputUserName(@Param("inputUserNameType") Integer inputUserNameType,
                                                                @Param("inputUserName") String inputUserName, @Param("sysType") Integer sysType);

}

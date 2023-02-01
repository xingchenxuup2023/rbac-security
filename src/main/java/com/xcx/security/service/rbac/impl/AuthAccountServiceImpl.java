package com.xcx.security.service.rbac.impl;

import com.xcx.security.vo.rbac.AuthAccount;
import com.xcx.security.mapper.rbac.AuthAccountMapper;
import com.xcx.security.service.rbac.AuthAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 统一账户信息 服务实现类
 * </p>
 *
 * @author 邢晨旭
 * @since 2023-02-01
 */
@Service
public class AuthAccountServiceImpl extends ServiceImpl<AuthAccountMapper, AuthAccount> implements AuthAccountService {

}

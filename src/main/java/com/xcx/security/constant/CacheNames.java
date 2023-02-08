package com.xcx.security.constant;

/**
 * 缓存名字
 *
 * @author xcx
 * @date 2023/2/7 17:23
 */
public interface CacheNames {
    /**
     *
     * 参考CacheKeyPrefix
     * cacheNames 与 key 之间的默认连接字符
     */
    String UNION = "::";

    /**
     * key内部的连接字符（自定义）
     */
    String UNION_KEY = ":";


    /**
     * 前缀
     */
    String RBAC_PREFIX = "xcx_rbac:";

    /**
     * 所有权限列表缓存key
     */
    String PERMISSIONS_KEY = RBAC_PREFIX + RBAC_PREFIX + "permission:permissions:";

    /**
     * 用户拥有的权限列表缓存key
     */
    String USER_PERMISSIONS_KEY = RBAC_PREFIX + "permission:user_permissions:";

    /**
     * uri对应的权限缓存key
     */
    String URI_PERMISSION_KEY = RBAC_PREFIX + "permission:uri_permissions:";

    /**
     * uri对应的权限缓存key
     */
    String MENU_LIST_KEY = RBAC_PREFIX + "menu:list:";

    /**
     * 菜单id key
     */
    String MENU_ID_LIST_KEY = RBAC_PREFIX + "menu:id_list:";


    /**
     * oauth 授权相关key
     */
    String OAUTH_PREFIX = "xcx_oauth:";

    /**
     * token 授权相关key
     */
    String OAUTH_TOKEN_PREFIX = OAUTH_PREFIX + "token:";

    /**
     * 保存token 缓存使用key
     */
    String ACCESS = OAUTH_TOKEN_PREFIX + "access:";

    /**
     * 刷新token 缓存使用key
     */
    String REFRESH_TO_ACCESS = OAUTH_TOKEN_PREFIX + "refresh_to_access:";

    /**
     * 根据uid获取保存的token key缓存使用的key
     */
    String UID_TO_ACCESS = OAUTH_TOKEN_PREFIX + "uid_to_access:";

}

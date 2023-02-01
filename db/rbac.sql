create table auth_account
(
    uid            bigint unsigned                                       not null comment '用户全局唯一id'
        primary key,
    username       varchar(30) charset utf8mb4 default ''                not null comment '用户名',
    password       varchar(64) charset utf8mb4 default ''                not null comment '密码',
    create_ip      varchar(15) charset utf8mb4 default ''                not null comment '创建ip',
    status         tinyint                                               not null comment '状态 1:启用 0:禁用 -1:删除',
    sys_type       tinyint                                               not null comment '用户类型 普通用户系统(0)/平台(1、2...)',
    user_id        bigint                                                not null comment '用户id',
    is_admin       tinyint                                               null comment '是否是管理员',
    create_time    datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time    datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    create_account bigint unsigned                                       not null comment '创建人',
    update_account bigint unsigned                                       not null comment '创建时间',
    constraint uk_usertype_userid
        unique (sys_type, user_id)
)
    comment '统一账户信息' charset = utf8mb4;

create index idx_username
    on auth_account (username);

create table menu
(
    menu_id        bigint unsigned auto_increment comment '菜单id'
        primary key,
    parent_id      bigint unsigned                    not null comment '父菜单ID，一级菜单为0',
    biz_type       tinyint                            null comment '业务类型（多平台使用）',
    permission     varchar(255)                       null comment '权限，需要有哪个权限才能访问该菜单',
    path           varchar(255)                       null comment '路径 就像uri',
    component      varchar(255)                       null comment '1.''Layout'' 为布局，不会跳页面 2.''components-demo/tinymce'' 跳转到该页面',
    redirect       varchar(255)                       null comment '当设置 noRedirect 的时候该路由在面包屑导航中不可被点击',
    always_show    tinyint                            null comment '一直显示根路由',
    hidden         tinyint                            null comment '当设置 true 的时候该路由不会在侧边栏出现 如401，login等页面，或者如一些编辑页面/edit/1',
    name           varchar(32)                        null comment '设定路由的名字，一定要填写不然使用<keep-alive>时会出现各种问题',
    title          varchar(32)                        null comment '设置该路由在侧边栏和面包屑中展示的名字',
    icon           varchar(32)                        null comment '设置该路由的图标，支持 svg-class，也支持 el-icon-x element-ui 的 icon',
    no_cache       tinyint                            null comment '如果设置为true，则不会被 <keep-alive> 缓存(默认 false)',
    breadcrumb     tinyint                            null comment '如果设置为false，则不会在breadcrumb面包屑中显示(默认 true)',
    affix          tinyint                            null comment '若果设置为true，它则会固定在tags-view中(默认 false)',
    active_menu    varchar(255)                       null comment '当路由设置了该属性，则会高亮相对应的侧边栏。',
    seq            int                                null comment '排序，越小越靠前',
    create_time    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time    datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    create_account bigint unsigned                    not null comment '创建人',
    update_account bigint unsigned                    not null comment '创建时间'
)
    comment '菜单管理' charset = utf8mb4;

create index idx_pid
    on menu (parent_id);

create table menu_permission
(
    menu_permission_id bigint unsigned auto_increment comment '菜单资源用户id'
        primary key,
    menu_id            bigint                             not null comment '资源关联菜单',
    biz_type           tinyint                            not null comment '业务类型（多平台使用）',
    permission         varchar(255)                       not null comment '权限对应的编码',
    name               varchar(32)                        not null comment '资源名称',
    uri                varchar(255)                       not null comment '资源对应服务器路径',
    method             tinyint                            not null comment '请求方法 1.GET 2.POST 3.PUT 4.DELETE',
    create_time        datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time        datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    create_account     bigint unsigned                    not null comment '创建人',
    update_account     bigint unsigned                    not null comment '创建时间',
    constraint uk_permission
        unique (permission, biz_type)
)
    comment '菜单资源' charset = utf8mb4;

create index idx_menu_id
    on menu_permission (menu_id);

create table role
(
    role_id        bigint unsigned auto_increment comment '角色id'
        primary key,
    role_name      varchar(100)                       not null comment '角色名称',
    remark         varchar(100)                       null comment '备注',
    create_user_id bigint unsigned                    not null comment '创建者ID',
    biz_type       tinyint                            null comment '业务类型（多平台使用）',
    create_time    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time    datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    create_account bigint unsigned                    not null comment '创建人',
    update_account bigint unsigned                    not null comment '创建时间'
)
    comment '角色' charset = utf8mb4;

create table role_menu
(
    id                 bigint unsigned auto_increment comment '关联id'
        primary key,
    role_id            bigint unsigned                    not null comment '角色ID',
    menu_id            bigint unsigned                    null comment '菜单ID',
    menu_permission_id bigint unsigned                    null comment '菜单资源用户id',
    create_time        datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time        datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    create_account     bigint unsigned                    not null comment '创建人',
    update_account     bigint unsigned                    not null comment '创建时间',
    constraint uk_role_menu_permission_id
        unique (role_id, menu_id, menu_permission_id)
)
    comment '角色与菜单对应关系' charset = utf8mb4;

create table user_role
(
    id             bigint unsigned auto_increment comment '关联id'
        primary key,
    user_id        bigint unsigned                    not null comment '用户ID',
    role_id        bigint unsigned                    not null comment '角色ID',
    create_time    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time    datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    create_account bigint unsigned                    not null comment '创建人',
    update_account bigint unsigned                    not null comment '创建时间',
    constraint uk_user_role_id
        unique (user_id, role_id)
)
    comment '用户与角色对应关系' charset = utf8mb4;



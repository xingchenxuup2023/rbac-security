package com.xcx.security.constant;

/**
 * 统一账户信息状态
 * @author xcx
 * @date 2023/2/8 11:07
 */
public enum AuthAccountStatusEnum {
    /**
     * 启用
     */
    ENABLE(1),

    /**
     * 禁用
     */
    DISABLE(0),

    /**
     * 删除
     */
    DELETE(-1)
    ;

    private final Integer value;

    public Integer value() {
        return value;
    }

    AuthAccountStatusEnum(Integer value) {
        this.value = value;
    }
}

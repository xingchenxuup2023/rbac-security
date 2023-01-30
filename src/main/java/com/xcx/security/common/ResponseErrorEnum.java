package com.xcx.security.common;

/**
 * @author 邢晨旭
 * @date 2023/1/30
 */
public enum ResponseErrorEnum {
    /**
     * ok
     */
    OK("000000", "ok"),

    /**
     * fail
     */
    FAIL("999999", "fail"),

    ;
    private final String code;

    private final String msg;

    public String value() {
        return code;
    }

    public String msg() {
        return msg;
    }

    ResponseErrorEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ResponseEnum{" + "code='" + code + '\'' + ", msg='" + msg + '\'' + "} " + super.toString();
    }
}

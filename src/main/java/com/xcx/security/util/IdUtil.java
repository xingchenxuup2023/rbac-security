package com.xcx.security.util;

import java.util.UUID;

/**
 * @author xcx
 * @date 2023/2/8 14:55
 */
public class IdUtil {
    public static String UUID() {
        return UUID.randomUUID().toString();
    }

    public static String simpleUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}

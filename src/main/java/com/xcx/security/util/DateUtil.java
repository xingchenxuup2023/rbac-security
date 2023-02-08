package com.xcx.security.util;

/**
 * @author xcx
 * @date 2023/2/8 11:38
 */
public class DateUtil {
    public static final int MINUTE_IN_MILLISECOND = 60 * 1000;
    public static final int MINUTE_IN_SECOND = 60;

    public static final int HOUR_IN_MILLISECOND = 60 * MINUTE_IN_MILLISECOND;
    public static final int HOUR_IN_SECOND = 60 * MINUTE_IN_SECOND;
    public static final int DAY_IN_SECOND = 24 * HOUR_IN_SECOND;
    public static final int WEEK_IN_SECOND = 7 * DAY_IN_SECOND;
}

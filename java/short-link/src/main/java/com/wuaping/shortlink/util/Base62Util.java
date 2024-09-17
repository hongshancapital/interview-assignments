package com.wuaping.shortlink.util;

/**
 * Base62工具
 *
 * @author Aping
 * @since 2022/3/19 12:14
 */
public class Base62Util {

    /**
     * 打乱字符，防止被爬虫
     */
    static final char[] BASE62 = "s97lqpOHi3oFIC51vwDdVZG6EXKTRmNgLufcBh4rYnt0zQPyaJ8kbU2xSejAWM".toCharArray();

    public static String encodeToLong(long value) {
        final StringBuilder sb = new StringBuilder();
        do {
            int i = (int) (value % 62);
            sb.append(BASE62[i]);
            value /= 62;
        } while (value > 0);
        return sb.toString();
    }

}

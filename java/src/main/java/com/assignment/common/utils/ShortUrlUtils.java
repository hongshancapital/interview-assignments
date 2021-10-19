package com.assignment.common.utils;

import java.util.concurrent.atomic.AtomicLong;

/**
 * shortUrl工具类
 *
 * @author shifeng
 */
public class ShortUrlUtils {

    private static AtomicLong num = new AtomicLong(888);


    /**
     * 根据自增id获取shortUrl
     *
     * @return
     */
    public static String getShortUrl() {
        long l = num.get();
        num.incrementAndGet();
        return toOtherBaseString(l);
    }

    /**
     * 在进制表示中的字符集合
     */
    final static char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
            'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    /**
     * 由10进制的数字转换到其他进制
     */
    private static String toOtherBaseString(long n) {
        long num;
        if (n < 0) {
            num = ((long) 2 * 0x7fffffff) + n + 2;
        } else {
            num = n;
        }
        char[] buf = new char[32];
        int charPos = 32;
        while ((num / DIGITS.length) > 0) {
            buf[--charPos] = DIGITS[(int) (num % DIGITS.length)];
            num /= 62;
        }
        buf[--charPos] = DIGITS[(int) (num % DIGITS.length)];
        return new String(buf, charPos, (32 - charPos));
    }
}

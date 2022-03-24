package com.sequoiacap.shorturl.utils;

/**
 * 62进制编码
 */
public class BaseEncoder {
    private final static int base = 62;
    private final static String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    /**
     * 十进制转62进制
     * @param number
     * @return
     */
    public static String encode(long number) {
        StringBuilder stringBuilder = new StringBuilder(1);
        do {
            stringBuilder.insert(0, characters.charAt((int) (number % base)));
            number /= base;
        } while (number > 0);
        return stringBuilder.toString();
    }

}

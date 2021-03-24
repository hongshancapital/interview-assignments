package com.wuhui.shorturl.utils;

/**
 * 数字工具类
 */
public abstract class NumberUtils {

    private static String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static int SCALE = 62;

    /**
     * 数字转62进制字符串
     */
    public static String encode62(long num) {
        StringBuilder sb = new StringBuilder();
        int remainder;
        while (num > SCALE - 1) {
            remainder = Long.valueOf(num % SCALE).intValue();
            sb.append(CHARS.charAt(remainder));
            num = num / SCALE;
        }
        sb.append(CHARS.charAt(Long.valueOf(num).intValue()));
        return sb.reverse().toString();
    }

    /**
     * 62进制字符串转数字
     */
    public static long decode62(String str) {
        long value = 0;
        char tempChar;
        int tempCharValue;
        for (int i = 0; i < str.length(); i++) {
            tempChar = str.charAt(i);
            tempCharValue = CHARS.indexOf(tempChar);
            value += (long) (tempCharValue * Math.pow(SCALE, str.length() - i - 1));
        }
        return value;
    }

}

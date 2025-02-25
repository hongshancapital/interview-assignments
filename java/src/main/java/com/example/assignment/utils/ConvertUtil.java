package com.example.assignment.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Wang Ying
 * @date 2022.5.5
 * @description 转换工具类
 */
public class ConvertUtil {
    private static String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static int scale = 62;
    private static int minLength = 8;

    /**
     * 将10进制数字转为62进制
     *
     * @param num Long 型数字
     * @return 62进制字符串
     */
    public static String base62Encode(long num) {
        StringBuilder sb = new StringBuilder();
        int remainder;
        while (num > scale - 1) {
            remainder = Long.valueOf(num % scale).intValue();
            sb.append(chars.charAt(remainder));
            num = num / scale;
        }
        sb.append(chars.charAt(Long.valueOf(num).intValue()));
        String value = sb.reverse().toString();
        return StringUtils.leftPad(value, minLength, '0');
    }

    /**
     * 62进制字符串转为十进制数字
     *
     * @param str 编码后的62进制字符串
     * @return 解码后的 10 进制字符串
     */
    public static long base62Decode(String str) {
        str = str.replace("^0*", "");
        long num = 0;
        int index = 0;
        for (int i = 0; i < str.length(); i++) {
            index = chars.indexOf(str.charAt(i));
            num += (long) (index * (Math.pow(scale, str.length() - i - 1)));
        }
        return num;
    }
}

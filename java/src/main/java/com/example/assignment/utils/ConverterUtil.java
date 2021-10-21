package com.example.assignment.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 进制工转换具类
 */
public class ConverterUtil {
    /**
     * 初始化 62 进制数据，索引位置代表转换字符的数值 0-61，比如 a代表10，Z代表61
     */
    private static String CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 进制转换比率
     */
    private static int SCALE = 62;

    /**
     * 将数字转为62进制
     *
     * @param num Long 型数字
     * @param length 转换后的字符串长度，不足则左侧补0
     * @return 62进制字符串
     */
    public static String encode10To62(long num, int length) {
        StringBuilder sb = new StringBuilder();
        int remainder;

        while (num > SCALE - 1) {
            remainder = Long.valueOf(num % SCALE).intValue();
            sb.append(CHARS.charAt(remainder));
            num = num / SCALE;
        }

        sb.append(CHARS.charAt(Long.valueOf(num).intValue()));
        String value = sb.reverse().toString();
        return StringUtils.leftPad(value, length, '0');
    }
}

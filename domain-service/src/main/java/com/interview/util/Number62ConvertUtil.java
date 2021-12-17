package com.interview.util;

/**
 * @Author: nyacc
 * @Date: 2021/12/17 12:55
 */


public class Number62ConvertUtil {


    /**
     * 初始化字符集
     */
    private static String totalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";

    private static int scale = 62;

    /**
     * 将数字转为62进制
     * @param num Long 型数字
     * @return  62进制字符串
     */
    public static String encode(long num) {
        StringBuilder sb = new StringBuilder();
        int remainder = 0;
        while (num > scale - 1) {
            remainder = Long.valueOf(num % scale).intValue();
            sb.append(totalChars.charAt(remainder));
            num = num / scale;
        }
        sb.append(totalChars.charAt(Long.valueOf(num).intValue()));
        String value = sb.reverse().toString();
        return value;
    }

}

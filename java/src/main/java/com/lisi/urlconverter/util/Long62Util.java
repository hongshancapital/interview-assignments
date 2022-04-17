package com.lisi.urlconverter.util;

/**
 * @description: long变量转换成62进制工具类
 * @author: li si
 */
public class Long62Util {
    private static final char[] ARR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    /**
     * 根据long计算其62进制
     * @param num
     * @return
     */
    public static String to62StringRevert(long num){
        StringBuilder builder = new StringBuilder();
        while(num > 0L){
            int index = (int)(num % 62L);
            num = num / 62L;
            builder.append(ARR[index]);
        }
        return builder.reverse().toString();
    }
}

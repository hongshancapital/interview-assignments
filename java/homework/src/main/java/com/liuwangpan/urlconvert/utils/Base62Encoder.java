package com.liuwangpan.urlconvert.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @Deacription 采用62进制存储10进制
 * @author wp_li
 **/
public class Base62Encoder {

    /**
     * 混合
     */
    private static String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    /**
     * 位数
     */
    private static int scale = 62;
    /**
     * 长度
     */
    private static int minLength = 5;

    /**
     * 10进制转62进制
     *
     * @param num
     * @return
     */
    public static String encode(long num) {
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
}
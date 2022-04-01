package com.getao.urlconverter.util;

import org.apache.commons.lang3.StringUtils;

public class ConverterUtil {

    // 62进制的字符，包含所有小写字母，大小字母及数字
    private static final String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    // 要转化成的进制
    private static final int scale = 62;

    // 62进制中的字符
    private static String REGEX = "^[0-9a-zA-Z]+$";

    /**
     * 10进制数字编码为62进制字符串
     *
     * @param  num 10进制数字
     * @return 62进制字符串
     */
    public static String encode(long num, int length) {
        StringBuilder sb = new StringBuilder();
        int remainder = 0;
        while (num > scale - 1) {
            remainder = Long.valueOf(num % scale).intValue();
            sb.append(chars.charAt(remainder));
            num = num / scale;
        }
        sb.append(chars.charAt(Long.valueOf(num).intValue()));
        String value = sb.reverse().toString();
        return StringUtils.leftPad(value, length, '0');
    }

    /**
     * 62进制字符串解码为10进制
     *
     * @param str 编码后的62进制字符串
     * @return 10进制数字
     */
    public static long decode(String str) {
        str = str.replace("^0*", ""); // 替换0开头的字符串
        long num = 0;
        int index = 0;
        for (int i = 0; i < str.length(); i++) {
            index = chars.indexOf(str.charAt(i));
            num += (long) (index * (Math.pow(scale, str.length() - i - 1)));
        }

        return num;
    }
}

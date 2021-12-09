package com.scdt.url.common.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

@UtilityClass
public final class Base64Utils {

    private static final String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int SCALE = 62;

    /**
     * 将10进制数字转为62进制
     *
     * @param num Long 型数字
     * @return 62进制字符串
     */
    public static String encode(long num, int length) {
        StringBuilder sb = new StringBuilder();
        int remainder;
        while (num > SCALE - 1) {
            remainder = (int) (num % SCALE);
            sb.append(CHARS.charAt(remainder));
            num = num / SCALE;
        }
        sb.append(CHARS.charAt((int) num));
        String value = sb.reverse().toString();
        return StringUtils.leftPad(value, length, '0');
    }

    /**
     * 62进制字符串转为十进制数字
     *
     * @param str 编码后的62进制字符串
     * @return 解码后的 10 进制字符串
     */
    public static long decode(String str) {
        str = str.replace("^0*", "");
        long num = 0;
        int index = 0;
        for (int i = 0; i < str.length(); i++) {
            index = CHARS.indexOf(str.charAt(i));
            num += (long) (index * (Math.pow(SCALE, str.length() - i - 1)));
        }
        return num;
    }

}
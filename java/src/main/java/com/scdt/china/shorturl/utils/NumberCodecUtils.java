package com.scdt.china.shorturl.utils;

import java.util.Arrays;

/**
 * 数字编解码工具类
 *
 * @author ng-life
 */
public class NumberCodecUtils {

    static char[] chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
    static int SIZE = chars.length;
    static byte[] charsIndex = new byte[127];

    static {
        Arrays.fill(charsIndex, (byte) -1);
        for (int i = 0; i < chars.length; i++) {
            char currentChar = chars[i];
            charsIndex[currentChar] = (byte) i;
        }
    }

    public static String encode(Long source) {
        if (source < 0) {
            throw new IllegalArgumentException("不支持负数：" + source);
        }
        StringBuilder codeBuilder = new StringBuilder(6);
        long remain = source;
        while (remain != 0) {
            int charIndex = (int) (remain % SIZE);
            remain = remain / SIZE;
            char charItem = chars[charIndex];
            codeBuilder.append(charItem);
        }
        if (codeBuilder.length() == 0) {
            return "0";
        }
        return codeBuilder.reverse().toString();
    }

    public static Long decode(String code) {
        char[] codeChars = code.toCharArray();
        long value = 0L;
        for (char currentChar : codeChars) {
            byte charsIndex = NumberCodecUtils.charsIndex[currentChar];
            if (charsIndex < 0) {
                throw new IllegalArgumentException("编码<" + code + ">包含非法字符: " + currentChar);
            }
            value = value * SIZE + charsIndex;
        }
        return value;
    }

}

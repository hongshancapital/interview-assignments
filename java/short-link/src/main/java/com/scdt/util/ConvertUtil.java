package com.scdt.util;

import java.util.Stack;

/**
 * ConvertUtil
 *
 * @author weixiao
 * @date 2022-04-26 14:15
 */
public class ConvertUtil {

    private static final char[] CHAR_SET_62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    private ConvertUtil() {
    }

    /**
     * 10进制转化为62进制
     *
     * @param number 10进制数
     * @param length 输出长度（不足以0填充）
     * @return 指定长度的62进制
     */
    public static String tenToSixtyTwo(long number, int length) {
        if (number < 0 || length < 0) {
            throw new IllegalArgumentException("param should be positive");
        }
        Long rest = number;
        Stack<Character> stack = new Stack<>();
        StringBuilder result = new StringBuilder();
        while (rest != 0) {
            stack.add(CHAR_SET_62[new Long((rest - (rest / 62) * 62)).intValue()]);
            rest = rest / 62;
        }
        for (; !stack.isEmpty(); ) {
            result.append(stack.pop());
        }
        int resultLength = result.length();
        StringBuilder padding = new StringBuilder();
        for (int i = 0; i < length - resultLength; i++) {
            padding.append('0');
        }

        return padding.toString() + result.toString();
    }
}

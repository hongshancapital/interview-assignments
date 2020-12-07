package com.bruce.shorturl.util;

import java.util.Stack;

/**
 * 算法工具类
 * @author bruce
 */
public class MathUtils {

    /** 大写英文+小写英文+10位数字构成的62个char */
    private static final char[] CHAR_ARRAY = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
    /** 位数 */
    private static final int CHAR_LENGTH = CHAR_ARRAY.length;


    /**
     * 将10进制转化为指定进制（62）
     *
     * @param num
     * @return
     */
    public static String _10_to_spec(long num) {
        long restNum = Math.abs(num);
        Stack<Character> stack = new Stack();
        StringBuilder result = new StringBuilder();
        while (restNum != 0) {
            char c = CHAR_ARRAY[new Long((restNum - (restNum / CHAR_LENGTH) * CHAR_LENGTH)).intValue()];
            stack.add(c);
            restNum = restNum / CHAR_LENGTH;
        }
        for (; !stack.isEmpty(); ) {
            result.append(stack.pop());
        }
        return result.toString();
    }



}

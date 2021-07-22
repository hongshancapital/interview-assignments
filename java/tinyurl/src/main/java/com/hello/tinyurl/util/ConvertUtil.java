package com.hello.tinyurl.util;


import org.springframework.util.StringUtils;

import java.util.Stack;

/**
 * @author: Shuai
 * @date: 2021-7-16 16:34
 * @description:
 */
public class ConvertUtil {

    /**
     * 乱序输出字符串
     */
    private static final char[] CHARS = {
            'S', 'd', '4', '5', '0', '2', 'f', 'g', 'H', 'I',
            'k', 'W', 'm', 'U', 'n', 'o', 'T', 'V', 'F', 'X',
            'u', 'v', 'w', '9', 'y', 'J', 'K', 'L', 'M', 'N',
            'E', 'B', '6', '7', 'l', 'C', '1', 'D', 'z', 'A',
            'O', 'P', 'a', 'R', 'Q', 'p', 'q', 'r', 's', 't',
            'b', 'Y', 'G', 'Z', 'h', 'i', 'j', 'c', 'e', '3',
            '8', 'x'
    };

    public static long string2Number(String str) throws Exception {
        if (!StringUtils.hasText(str)) {
            throw new Exception("input string is empty.");
        }
        if (str.length() > 9) {
            throw new Exception("input string " + str + " longer then 8 characters.");
        }
        char[] chars = str.toCharArray();
        char c;
        long l = 1L;
        long num = 0L;
        for (int i = 0; i < str.length(); i++) {
            c = chars[str.length() - i - 1];
            num += (getValue(c) * l);
            l *= 62;
        }
        return num;
    }

    private static int getValue(char c) throws Exception {
        for (int i = 0; i < CHARS.length; i++) {
            if (CHARS[i] == c)
                return i;
        }
        throw new Exception("contains illegal character '" + c + "'.");
    }

    public static String num2String(Long num) throws Exception {
        if (num == null){
            throw new Exception("input id is empty.");
        }
        if (num >= 218340105584896L)  // 62L * 62 * 62 * 62 * 62 * 62 * 62 * 62
            throw new Exception("out of maximum tiny url length.");
        Stack<Character> stack = new Stack<>();
        int l;
        num += 218340105584896L;
        while (0 < num) {
            l = (int) (num % 62);
            stack.push(CHARS[l]);
            num = num / 62;
        }
        stack.pop();
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty())
            sb.append(stack.pop());
        return sb.toString();
    }

}

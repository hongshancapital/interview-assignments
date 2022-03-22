package com.zoujing.shortlink.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class HexTransformatUtil {
    /**
     * 将十进制转换为任意进制值
     *
     * @param digths 转换后的进制最小位上，依次出现的字符值，比如26进制"ABCDEFGHIJKLMNOPQRSTUVWXYZ"
     * @param num    将被转换的十进制值
     * @param length 转换到指定字符串后，如果不足length长度位，自动补足最小值，比如26进制"ABCDEFGHIJKLMNOPQRSTUVWXYZ"将在最前补"a"
     * @return
     */
    public static String hex10ToAnly(String digths, long num, long length) {
        StringBuffer str = new StringBuffer("");
        int base = digths.trim().length();
        if (0 == num) {
            str.append(digths.charAt(0));
        } else {
            Stack<Character> s = new Stack<Character>();
            while (num != 0) {
                s.push(digths.charAt((int) num % base));
                num /= base;
            }
            while (!s.isEmpty()) {
                str.append(s.pop());
            }
        }
        String prefix = "";
        String suffix = str.toString();
        if (length > suffix.length()) {
            for (int count = 0; count < length - suffix.length(); count++) {
                prefix = prefix + digths.charAt(0);
            }
        }
        return prefix + suffix;
    }

    /**
     * 将任意进制转换为十进制值
     *
     * @param digths   转换前的进制最小位上，依次出现的字符值，比如26进制"ABCDEFGHIJKLMNOPQRSTUVWXYZ"
     * @param hexValue 转换前的进制字符串值
     * @return
     */
    public static int hexAnlyTo10(String digths, String hexValue) {
        if (null == hexValue || "".equals(hexValue.trim())) return 0;
        int base = digths.trim().length();

        Map<String, Integer> digthMap = new HashMap<String, Integer>();
        int count = 0;
        for (char item : digths.trim().toCharArray()) {
            digthMap.put("" + item, count);
            count++;
        }
        String str = new StringBuffer(hexValue.trim()).reverse().toString();
        int sum = 0;
        for (int index = 0; index < str.length(); index++) {
            sum = new Double(Math.pow(base, index)).intValue() * digthMap.get("" + str.charAt(index)) + sum;
        }
        return sum;
    }
}

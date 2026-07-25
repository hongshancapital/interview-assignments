package com.interview.wph.shorturl.common.utils;

import com.interview.wph.shorturl.common.consts.SpringConst;

import java.util.HashMap;
import java.util.Map;

public class EncryptionUtil {
    private static Integer MAX_LEN;
    private final static Integer DECIMAL = 62;
    private static Long COEFFICIENT;
    static final char[] CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
            'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
            'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
            'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public static Map<Character, Integer> CHAR_VALUE_MAP = new HashMap<Character, Integer>();

    //初始化map
    static {
        for (int i = 0; i < CHARS.length; i++) CHAR_VALUE_MAP.put(CHARS[i], i);
        MAX_LEN = SpringUtil.getBean(SpringConst.class).getMaxShortLen();
        COEFFICIENT = new Double(Math.pow(DECIMAL, MAX_LEN)).longValue();
    }

    public static Long generateId() {
        return new Double(Math.random() * COEFFICIENT).longValue();
    }


    public static String to62RadixString(long number) {
        StringBuilder builder = new StringBuilder();
        while (number != 0) {
            builder.append(CHARS[(int) (number - (number / DECIMAL) * DECIMAL)]);
            number /= DECIMAL;
        }
        return builder.reverse().toString();
    }


    public static long decimalConvertToNumber(String decimalStr) {
        long sum = 0;
        long multiple = 1;
        char[] chars = decimalStr.toCharArray();
        for (int i = chars.length - 1; i >= 0; i--) {
            char c = chars[i];
            sum += CHAR_VALUE_MAP.get(c) * multiple;
            multiple *= DECIMAL;
        }
        return sum;
    }
}

package com.scdt.shortlink.util;

import java.util.regex.Pattern;

public class Transformer {
    // 短域名校验正则表达式
    final static Pattern keyPattern = Pattern.compile("^[a-zA-Z0-9]{3,8}$");

    private static final char[] lookup = new char[] { 'A' , 'o' , 'J' , 'Y' , 'p' , 'G' , 'h' ,
            'Q' , 'F' , 'X' , 'U' , 'a' , 'H' , 'q' , '1' , 'g' , 't' , 'y' , '7' , 'M' , 'T' ,
            'K' , 'P' , 'S' , 'Z' , 'e' , 'I' , '5' , 'k' , 'j' , 'n' , 'b' , 'W' , 'E' , 'r' ,
            'L' , 'O' , 'R' , 'C' , '9' , 's' , 'l' , '4' , 'z' , 'V' , '0' , 'd' , 'v' , 'f' ,
            '3' , 'D' , 'i' , 'u' , 'N' , '6' , 'w' , '8' , '2' , 'x' , 'B' , 'm' , 'c'
    };
    private static final int LOOKUP_LENGTH = lookup.length;
    private static final long MAX_ID_LINK8 = Double.valueOf(Math.pow(lookup.length, 8)).longValue();

    public static String transform8(long id) {
        if (id > MAX_ID_LINK8)
            return null;

        return transform(id, 8);
    }

    public static long getId(String key) {
        long id = 0;
        long lastPow = 1;
        char[] keyArray = key.toCharArray();

        for (int i = key.length(); i > 0; i--) {
            int idx = indexOfLookup(keyArray[i - 1]);
            id += idx * lastPow;
            lastPow *= LOOKUP_LENGTH;
        }
        return id;
    }

    private static String transform(long id, int length) {
        char[] keyArray = new char[length];
        int i = 0;

        while (i < length) {
            int idx = (int) (id % LOOKUP_LENGTH);
            keyArray[length - ++i] = lookup[idx];
            id = id / LOOKUP_LENGTH;
        }

        return String.valueOf(keyArray);
    }

    private static int indexOfLookup(char ch) {
        for (int i = 0; i < LOOKUP_LENGTH; i++) {
            if (lookup[i] == ch) {
                return i;
            }
        }
        return -1;
    }

    //判断key是否符合短域名规范
    public static boolean validKey(String key) {
        return keyPattern.matcher(key).matches();
    }
}

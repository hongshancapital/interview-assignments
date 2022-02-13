package com.liupf.tiny.url.utils;

/**
 * 生成TinyCode编号，10进制Id -> 64进制Code
 */
public final class Code62Util {

    private static final char[] LOOKUP = new char[] {'A', 'o', 'J', 'Y', 'p', 'G', 'h',
            'Q', 'F', 'X', 'U', 'a', 'H', 'q', '1', 'g', 't', 'y', '7', 'M', 'T',
            'K', 'P', 'S', 'Z', 'e', 'I', '5', 'k', 'j', 'n', 'b', 'W', 'E', 'r',
            'L', 'O', 'R', 'C', '9', 's', 'l', '4', 'z', 'V', '0', 'd', 'v', 'f',
            '3', 'D', 'i', 'u', 'N', '6', 'w', '8', '2', 'x', 'B', 'm', 'c'
    };
    private static final int CODE_LENGTH = 8;
    private static final int LOOKUP_LENGTH = LOOKUP.length;
    /**
     * 64进制下8位长度的最大值218340105584896
     */
    private static final long MAX_ID_LINK8 = Double.valueOf(Math.pow(LOOKUP.length, CODE_LENGTH)).longValue();

    /**
     * 10进制数字转成64进制
     */
    public static String convertTo64(long id) {
        if (id > MAX_ID_LINK8) {
            return null;
        }
        char[] keyArray = new char[CODE_LENGTH];
        int i = 0;
        while (i < CODE_LENGTH) {
            int idx = (int) (id % LOOKUP_LENGTH);
            keyArray[CODE_LENGTH - ++i] = LOOKUP[idx];
            id = id / LOOKUP_LENGTH;
        }
        return String.valueOf(keyArray);
    }

}

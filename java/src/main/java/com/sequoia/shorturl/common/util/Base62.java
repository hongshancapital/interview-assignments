package com.sequoia.shorturl.common.util;

/**
 * @Author: yanggj
 * @Description: TODO
 * @Date: 2022/1/9 15:36
 * @Version: 1.0.0
 */
public class Base62 {

    /**
     * GMP风格
     */
    private static final char[] GMP = { //
            '0', '1', '2', '3', '4', '5', '6', '7', //
            '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', //
            'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', //
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', //
            'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', //
            'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', //
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', //
            'u', 'v', 'w', 'x', 'y', 'z' //
    };
    public static final int RADIX = GMP.length;

    public static String encode(long num) {
        StringBuilder result = new StringBuilder();
        while (num > 0) {
            int mod = (int) (num % RADIX);
            result.insert(0, GMP[mod]);
            num = num / RADIX;
        }
        return result.toString();
    }

}

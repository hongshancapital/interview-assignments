package com.wxp.util;

import java.util.Arrays;

/**
 * 62进制与10进制转换类
 * @author wxp
 */
public class Base62 {

    private static final char[] toBase62 = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    private static final int[] fromBase62 = new int[128];
    private static final int RADIX = 62;

    static {
        Arrays.fill(fromBase62, -1);
        for (int i = 0; i < toBase62.length; i++) {
            fromBase62[toBase62[i]] = i;
        }
    }

    private Base62() {

    }


    public static String encode(Long l) {
        StringBuilder stringBuilder = new StringBuilder();
        while (l > 0) {
            stringBuilder.append(toBase62[(int) (l % RADIX)]);
            l /= RADIX;
        }
        return stringBuilder.reverse().toString();
    }

    public static long decode(String s) {

        long l = 0L;
        long d = 1L;
        for (int i = s.length() - 1; i >= 0; i--) {
            l = l + d * fromBase62[s.charAt(i)];
            d *= RADIX;
        }
        return l;
    }
}
package com.example.shorturl.util;

/**
 * 十进制和62进制相互转换的工具类
 *
 * @author yingchen
 * @date 2022/3/15
 */
public class Base62Util {
    public static final String BASE_62_CHAR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static final int BASE = BASE_62_CHAR.length();

    public static String base10ToBase62(long i) {
        StringBuilder sb = new StringBuilder("");
        if (i == 0) {
            return "a";
        }
        while (i > 0) {
            i = fromBase10(i, sb);
        }
        return sb.reverse().toString();
    }

    private static long fromBase10(long i, final StringBuilder sb) {
        int rem = (int) (i % BASE);
        sb.append(BASE_62_CHAR.charAt(rem));
        return i / BASE;
    }

    public static long base62ToBase10(String str) {
        return toBase10(new StringBuilder(str).reverse().toString().toCharArray());
    }


    private static long toBase10(char[] chars) {
        long n = 0;
        int pow = 0;
        for (char item : chars) {
            n += toBase10(BASE_62_CHAR.indexOf(item), pow);
            pow++;
        }
        return n;
    }

    private static long toBase10(int n, int pow) {
        return n * (long) Math.pow(BASE, pow);
    }
}

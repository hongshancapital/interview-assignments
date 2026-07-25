package com.example.shortlink.util;


/**
 * utils for short - long link convert framework
 */
public class ShortLinkUtils {


    private static final String BASE = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String toBase62(long num) {
        StringBuilder sb = new StringBuilder();
        do {
            int i = (int) (num % 62);
            sb.append(BASE.charAt(i));
            num /= 62;
        } while (num > 0);

        return sb.reverse().toString();
    }

}

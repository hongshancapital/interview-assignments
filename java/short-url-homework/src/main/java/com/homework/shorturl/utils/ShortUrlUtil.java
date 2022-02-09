package com.homework.shorturl.utils;

import java.net.URL;

public class ShortUrlUtil {

    // 自定义短码表，无序是为了安全考虑
    private static final String ALPHABET = "xyzabcdefghijklmnopqrstuvwXYZABCDEFGHIJKLMNOPQRSTUVW7890123456";
    private static final int BASE = ALPHABET.length();

    public static String idToString(long num) {
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            sb.append(ALPHABET.charAt(Math.toIntExact(num % BASE)));
            num /= BASE;
        }
        return sb.reverse().toString();
    }

    public static Long stringToId(String str) {
        long num = 0L;
        for (int i = 0; i < str.length(); i++) {
            num = num * BASE + ALPHABET.indexOf(str.charAt(i));
        }
        return num;
    }

    // TODO: 待优化。 这个办法不是最好的办法，例如带有IP的长链接，ftp prefix链接等不适用
    public static boolean isValidUrl(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

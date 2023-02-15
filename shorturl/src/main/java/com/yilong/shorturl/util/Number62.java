package com.yilong.shorturl.util;

public class Number62 {
    public static String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int BASE = 62;

    public static String encode(long num) {
        if (num < 0) {
            throw new IllegalArgumentException("num must be greater than 0.");
        }

        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            sb.append(ALPHABET.charAt((int) (num % BASE)));
            num /= BASE;
        }

        return (sb.length() == 0) ? ALPHABET.substring(0, 1) : sb.reverse().toString();
    }

    public static long decode(String str) {
        if (str == null || str.length() == 0 ){
            throw new IllegalArgumentException("str must not be empty.");
        }

        long result = 0;
        int len = str.length();
        int alphabetIndex;
        for (int i = 0; i < len; i++) {
            alphabetIndex = ALPHABET.indexOf(str.charAt(len - 1 - i));
            if (alphabetIndex < 0) {
                throw new IllegalArgumentException("str char must be alphabet.");
            }
            result += alphabetIndex * Math.pow(BASE, i);
        }

        return result;
    }

    private Number62(){}
}
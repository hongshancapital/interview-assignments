package com.oldnoop.shortlink.util;

public class Radix62Transfer {
    private static final String CHARS = "abcde0fghij1klmno2pqrst3uvwxy4zABCD5EFGHI6JKLMN7OPQRS8TUVWX9YZ";
    private static final int SCALE = 62;

    public static String to(long num) {
        StringBuilder sb = new StringBuilder();
        int remainder;
        while (num > SCALE - 1) {
            remainder = (int) (num % SCALE);
            sb.append(CHARS.charAt(remainder));
            num = num / SCALE;
        }
        sb.append(CHARS.charAt((int) num));
        String value = sb.reverse().toString();
        return value;
    }

    public static long from(String str) {
        str = str.replace("^0*", "");
        long value = 0;
        char tempChar;
        int tcv;
        for (int i = 0; i < str.length(); i++) {
            tempChar = str.charAt(i);
            tcv = CHARS.indexOf(tempChar);
            value += (long) (tcv * Math.pow(SCALE, str.length() - i - 1));
        }
        return value;
    }
}

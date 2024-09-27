package com.url.util;

public class DataConversionUtil {

    static final char[] DIGITS =
            {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                    'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                    'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
                    'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                    'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    public static String converse(long num) {
        StringBuilder builder = new StringBuilder();
        while (true) {
            int remainder = (int) (num % 62);
            builder.append(DIGITS[remainder]);
            num = num / 62;
            if (num == 0) {
                break;
            }
        }
        return builder.reverse().toString();
    }

    public static long reConverse(String data) {
        long sum = 0;
        int len = data.length();
        for (int i = 0; i < len; i++) {
            sum += indexDigits(data.charAt(len - i - 1)) * Math.pow(62, i);
        }
        return sum;
    }

    private static int indexDigits(char ch) {
        for (int i = 0; i < DIGITS.length; i++) {
            if (ch == DIGITS[i]) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        long l = 903954751484329984l;
        System.out.println(converse(l));
        System.out.println(reConverse("ZZZZZZZZ"));
        System.out.println(reConverse("10000000"));
        System.out.println(System.currentTimeMillis());
    }

}

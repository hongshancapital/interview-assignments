package com.dblones.shortlink.util;

import java.util.concurrent.atomic.AtomicLong;

public class NumberUtils {

    private final static char[] digits = { 's', 'x', 'm', 'd', 'z', 'C', 'e', 't', 'g', 'D',
            'j', 'b', 'k', 'l', 'A', 'i', 'n', 'o', 'F', 'p', 'r', 'a', 'f', 'u', 'y', 'v', 'E',
            'w', 'h', 'B', 'q', 'c'};

    public static String compressNumber(long number) {
        char[] buf = new char[32];
        int charPos = 32;
        int radix = 1 << 5;
        long mask = radix - 1;
        do {
            buf[--charPos] = digits[(int) (number & mask)];
            number >>>= 5;
        } while (number != 0);
        return new String(buf, charPos, (32 - charPos));
    }

    public static void main(String[] args) {
        long num = 2394278347l;
        String result = compressNumber(num);
        System.out.println(result);
    }
}

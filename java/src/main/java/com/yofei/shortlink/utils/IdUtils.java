package com.yofei.shortlink.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IdUtils {

    private static final String baseDigits = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!$";
    private static final int BASE = baseDigits.length();
    private static final char[] digitsChar = baseDigits.toCharArray();
    private static final int FAST_SIZE = 'z';
    private static final int[] digitsIndex = new int[FAST_SIZE + 1];

    static {
        for (int i = 0; i < FAST_SIZE; i++) {
            digitsIndex[i] = -1;
        }
        for (int i = 0; i < BASE; i++) {
            digitsIndex[digitsChar[i]] = i;
        }
    }

    public static long decode(String s) {
        long result = 0L;
        long multiplier = 1;
        for (int pos = s.length() - 1; pos >= 0; pos--) {
            int index = getIndex(s, pos);
            result += index * multiplier;
            multiplier *= BASE;
        }
        return result;
    }

    public static long toID(String value) {
        long result = 0L;

        for (int i = value.length() - 1; i >= 0; i--) {
            int index = getIndex(value, i);
            result = result  |(((long)index) << ((value.length() - 1-i)*6));

        }

        return result;
    }

    public static String toCode(long number) {
        if (number == 0)
            return "0";
        StringBuilder buf = new StringBuilder();
        int i = 0;
        while ((number >> (i*6)) != 0){
            long index = (number >> (i*6)) & 0X3F;
            buf.append(digitsChar[(int)index]);
            i++;
        }

        return buf.reverse().toString();
    }


    public static String encode(long number) {
        if (number < 0) {
            throw new IllegalArgumentException("Number(Base64) must be positive: " + number);
        }
        if (number == 0) {
            return "0";
        }
        StringBuilder buf = new StringBuilder();
        while (number != 0) {
            buf.append(digitsChar[(int) (number % BASE)]);
            number /= BASE;
        }
        return buf.reverse().toString();
    }

    private static int getIndex(String s, int pos) {
        char c = s.charAt(pos);

        int index = digitsIndex[c];
        if (index == -1) {
            log.error("Unknow character for Base64: " + s);
        }
        return index;
    }


    public static String md5(String value) {
        return DigestUtils.md5DigestAsHex(value.getBytes());
    }


}

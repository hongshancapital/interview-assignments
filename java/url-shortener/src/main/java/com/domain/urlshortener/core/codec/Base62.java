package com.domain.urlshortener.core.codec;

import java.math.BigInteger;

/**
 * @author: rocky.hu
 * @date: 2022/4/6 0:28
 */
public class Base62 {

    public static final BigInteger BASE = BigInteger.valueOf(62);
    public static final String DIGITS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    public static final String REGEXP = "^[0-9A-Za-z]+$";

    public static String encode(BigInteger number) {
        if (number.compareTo(BigInteger.ZERO) == -1) {
            throw new IllegalArgumentException("number must not be negative");
        }
        StringBuilder result = new StringBuilder();
        while (number.compareTo(BigInteger.ZERO) == 1) {
            BigInteger[] divmod = number.divideAndRemainder(BASE);
            number = divmod[0];
            int digit = divmod[1].intValue();
            result.insert(0, DIGITS.charAt(digit));
        }
        return (result.length() == 0) ? DIGITS.substring(0, 1) : result.toString();
    }

    public static String encode(long number) {
        return encode(BigInteger.valueOf(number));
    }

    public static BigInteger decode(final String string) {
        if (string.length() == 0) {
            throw new IllegalArgumentException("string must not be empty");
        }
        BigInteger result = BigInteger.ZERO;
        int digits = string.length();
        for (int index = 0; index < digits; index++) {
            int digit = DIGITS.indexOf(string.charAt(digits - index - 1));
            result = result.add(BigInteger.valueOf(digit).multiply(BASE.pow(index)));
        }
        return result;
    }

}

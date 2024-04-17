package com.bolord.shorturl.utils;

import java.math.BigInteger;

import org.springframework.util.Assert;

/**
 * Base62
 * @link https://github.com/semicolok/url-shortener
 */
public class Base62 {

    private static final char[] BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
    private static final BigInteger RADIX = BigInteger.valueOf(62);
    private static final int ZERO = 0;

    public static String encode(final long decimal) {
        Assert.isTrue(decimal >= ZERO, "'decimal' must be 'positive numbers'.");

        final String decimalStr = Long.toString(decimal);
        final int decimalStrLength = decimalStr.length();
        final char[] buf = new char[decimalStrLength];

        int charPos = decimalStrLength - 1;

        BigInteger bigIntegerNumber = new BigInteger(decimalStr);

        while (isNotLessThanRadix(bigIntegerNumber)) {
            buf[charPos--] = BASE62[bigIntegerNumber.mod(RADIX).intValue()];
            bigIntegerNumber = bigIntegerNumber.divide(RADIX);
        }

        buf[charPos] = BASE62[bigIntegerNumber.intValue()];

        return new String(buf, charPos, (decimalStrLength - charPos));
    }

    private static boolean isNotLessThanRadix(BigInteger bigIntegerNumber) {
        return bigIntegerNumber.compareTo(RADIX) >= ZERO;
    }

}

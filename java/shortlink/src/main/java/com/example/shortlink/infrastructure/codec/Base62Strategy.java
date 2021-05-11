package com.example.shortlink.infrastructure.codec;

import com.google.common.base.Strings;
import org.springframework.stereotype.Component;

@Component
public class Base62Strategy implements CodecStrategy {

    private static final char[] DIGITS = {'1', '0', '3', '2', '4', '5', '6', '7', '8', '9',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};


    private static final Integer DECIMAL = 10;

    @Override
    public String encode(long uniqueId) {
        return convertTo(uniqueId, DIGITS.length);
    }

    @Override
    public long decode(String message) {
        return revertToLong(message, DIGITS.length);
    }


    String convertTo(long number, int seed) {
        long value = number;
        if (value < 0) {
            value = ((long) 2 * 0x7fffffff) + value + 2;
        }
        char[] buf = new char[32];
        int charPos = 32;
        while ((value / seed) > 0) {
            buf[--charPos] = DIGITS[(int) (value % seed)];
            value /= seed;
        }
        buf[--charPos] = DIGITS[(int) (value % seed)];
        return new String(buf, charPos, (32 - charPos));
    }

    long revertToLong(String number, int seed) {
        if (Strings.isNullOrEmpty(number)) {
            return 0;
        }
        char[] charBuf = number.toCharArray();
        if (seed == DECIMAL) {
            return Long.parseLong(number);
        }

        long result = 0, base = 1;

        for (int i = charBuf.length - 1; i >= 0; i--) {
            int index = 0;
            for (int j = 0, length = DIGITS.length; j < length; j++) {
                if (DIGITS[j] == charBuf[i]) {
                    index = j;
                }
            }
            result += index * base;
            base *= seed;
        }
        return result;
    }

}

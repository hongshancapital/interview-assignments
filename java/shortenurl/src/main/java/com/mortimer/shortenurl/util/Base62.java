package com.mortimer.shortenurl.util;

import org.apache.commons.lang3.ArrayUtils;

public class Base62 {
    private static int BASE = 62;
    private static char[] CHARS;
    private static Base62 instance = new Base62();

    private Base62() {
        CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
        ArrayUtils.shuffle(CHARS);
    }

    public static Base62 getInstance() {
        return instance;
    }

    public String encode(long number) {
        if (number < 0) {
            throw new IllegalArgumentException("number can not be negative");
        }
        StringBuilder sb = new StringBuilder();
        int remainder = 0;
        while (number > BASE - 1) {
            remainder = (int)(number % BASE);
            sb.append(CHARS[remainder]);
            number = number / BASE;
        }
        sb.append(CHARS[(int)number]);
        return sb.reverse().toString();
    }
}

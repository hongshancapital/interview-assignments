package com.eagle.shorturl.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author eagle
 * @description
 */
public class EncodeUtil {

    private static final char[] CHAR_62 = new char[]{'0','1','2','3','4','5','6','7','8','9',
            'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
            'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    private static final int SCALE_62 = 62;

    public static String encode62(long number) {
        StringBuilder builder = new StringBuilder();
        int remainder;
        while (number > SCALE_62 - 1) {
            remainder = (int) (number % SCALE_62);
            builder.append(CHAR_62[remainder]);
            number = number / SCALE_62;
        }
        builder.append(CHAR_62[(int) number]);
        return StringUtils.reverse(builder.toString());
    }

}

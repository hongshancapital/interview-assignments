package com.example.tinyurl.impl;

/**
 * Helper for SimpleTinyUrlGenerator
 * @author hermitriver
 */
public class SimpleTinyUrlHelper {
    /** Character for an invalid value */
    public final static char INVALID_CHAR = ' ';

    /** Value for invalid tiny url */
    public final static byte INVALID_VALUE = -1;

    /** The number of maximum bits of a tiny url */
    public final static byte BITS = 8;

    /** The number of characters in single bit */
    public final static byte MODE = 62;

    /** The value for the maximum tiny url */
    public final static long MAX = 218340105584895L; // power(62, 8) - 1

    /**
     * Map byte value to a character
     * @param value byte value for a character
     * @return mapped character
     */
    public static char map(byte value) {
        if (value>=0 && value<=9) {
            return (char) (value + '0');
        } else if (value>=10 && value<=35) {
            return (char) (value-10 + 'A');
        } else if (value>=36 && value<=61) {
            return (char) (value-36 + 'a');
        }
        return INVALID_CHAR;
    }

    /**
     * Map long value to tiny url
     * @param value long value to be mapped to text
     * @return mapped tiny url
     */
    public static String map(long value) {
        if (value<0 || value> MAX) {
            return null;
        }
        StringBuffer url = new StringBuffer();
        do {
            url.insert(0, map((byte) (value % MODE)));
            value = value / MODE;
        } while (value > 0);
        return url.toString();
    }

    /**
     * Map a character to its byte value
     * @param ch a character
     * @return mapped byte value
     */
    public static byte map(char ch) {
        if (ch>='0' && ch<='9') {
            return (byte) (ch - '0');
        } else if (ch>='A' && ch<='Z') {
            return (byte) ((ch - 'A') + 10);
        } else if (ch>='a' && ch<='z') {
            return (byte) ((ch - 'a') + 36);
        }
        return INVALID_VALUE;
    }

    /**
     * Map tiny url to its long value
     * @param tinyUrl tiny url String
     * @return mapped long value
     */
    public static long map(String tinyUrl) {
        if (tinyUrl == null || "".equals(tinyUrl)) {
            return INVALID_VALUE;
        }
        if (tinyUrl.length() > BITS) {
            return INVALID_VALUE;
        }

        long value = 0;
        for (byte i=0; i<tinyUrl.length(); i++) {
            char ch = tinyUrl.charAt(i);
            byte bitValue = map(ch);
            if (bitValue == INVALID_VALUE) {
                return INVALID_VALUE;
            }
            value = (value * MODE) + bitValue;
        }
        return value;
    }
}

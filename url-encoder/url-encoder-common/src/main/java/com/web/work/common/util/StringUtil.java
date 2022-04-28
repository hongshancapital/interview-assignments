package com.web.work.common.util;

import com.google.common.hash.Hashing;

/**
 * string util
 *
 * @author chenze
 * @version 1.0
 * @date 2022/4/26 9:12 PM
 */
public class StringUtil {

    static final char[] DIGITS =
        {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
            'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
            'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    /**
     * generate string of 62 radix
     *
     * @param seq hash value
     * @return 62radix code
     */
    public static String to62RadixString(long seq) {
        StringBuilder sb = new StringBuilder();
        do {
            int remainder = (int)(seq % 62);
            sb.append(DIGITS[remainder]);
            seq = seq / 62;
        } while (seq != 0);
        return sb.toString();
    }

    /**
     * generate 62-radix code by guava util
     *
     * @param fullUrl full url
     * @return 62radix code
     */
    public static String generate62RadixCode(String fullUrl) {
        long seq = Hashing.murmur3_32().hashUnencodedChars(fullUrl).padToLong();
        return StringUtil.to62RadixString(seq);
    }
}

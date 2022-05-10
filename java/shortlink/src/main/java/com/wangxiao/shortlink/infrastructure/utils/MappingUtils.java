package com.wangxiao.shortlink.infrastructure.utils;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class MappingUtils {
    private MappingUtils() {
    }

    private static final char[] toBase62 = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    private static final int RADIX = 62;


    public static Long hashing(String text) {
        HashFunction hashFunction = Hashing.murmur3_32();
        return hashFunction.hashString(text, StandardCharsets.UTF_8).padToLong();
    }


    public static String encodeBase62(Long l) {
        StringBuilder stringBuilder = new StringBuilder();
        while (l > 0) {
            stringBuilder.append(toBase62[(int) (l % RADIX)]);
            l /= RADIX;
        }
        return stringBuilder.reverse().toString();
    }
}

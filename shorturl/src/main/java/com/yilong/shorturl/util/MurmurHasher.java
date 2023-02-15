package com.yilong.shorturl.util;

import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;

public class MurmurHasher {

    public static long hash(String origin) {
        if (origin == null || origin.length() == 0) {
            throw new IllegalArgumentException("origin must not be empty.");
        }
        return Hashing.murmur3_32().hashString(origin, StandardCharsets.UTF_8).padToLong();
    }

    private MurmurHasher(){}
}

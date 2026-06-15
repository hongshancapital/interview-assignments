package com.shortlink.util;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class MurmurHashUtil {
    public static byte[] hash(String originString){
        return Hashing.murmur3_128().newHasher().putString(originString, StandardCharsets.UTF_8).hash().asBytes();
    }
}

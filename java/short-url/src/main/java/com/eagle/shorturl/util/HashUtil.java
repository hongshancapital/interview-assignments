package com.eagle.shorturl.util;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * @author eagle
 * @description
 */
public class HashUtil {

    public static long murmur32(String key) {
        HashFunction hashFunction = Hashing.murmur3_32_fixed();
        return hashFunction.hashString(key, StandardCharsets.UTF_8).padToLong();
    }

    public static String hmacSHA256(String salt, String key) {
        SecretKeySpec secretKey = new SecretKeySpec(salt.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        HashFunction hashFunction = Hashing.hmacSha256(secretKey);
        return hashFunction.hashString(key, StandardCharsets.UTF_8).toString();
    }

}

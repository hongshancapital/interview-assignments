package com.ryr.homework.common.util;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * 短域名转换工具类
 */
@Component
public class ShortUrlConvertUtil {

    private final static String MD5_KEY = "ryr";

    private final static char[] CHARS = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
            'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z'};

    public String getShortUrl(String longUrl) {
        //guava工具包MurMurHash算法 生成32位hash
        HashFunction hashFunction = Hashing.murmur3_32();
        HashCode hashCode = hashFunction.hashString(longUrl, StandardCharsets.UTF_8);
        return this.to62Hex(hashCode.hashCode());
    }

    private String to62Hex(int hash) {
        hash = Math.abs(hash);

        StringBuilder sb = new StringBuilder();
        int remainder;

        for (int i = 0; i < 6; i++) {
            remainder = Long.valueOf(hash % 62).intValue();
            sb.append(CHARS[remainder]);
            hash = hash / 62;
        }
        return sb.reverse().toString();
    }
}

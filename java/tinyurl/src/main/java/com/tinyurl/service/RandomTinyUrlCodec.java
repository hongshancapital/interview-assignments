package com.tinyurl.service;

import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class RandomTinyUrlCodec {
    private static final String CS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int DIGITS = 8;
    private static final int TOTAL_DIGITS = 62;
    private static final String PREFIX = "http://tinyurl.com/";

    private static Map<String, String> tinyUrlMap = new ConcurrentHashMap<>();
    private static Map<String, String> reverseTinyUrlMap = new ConcurrentHashMap<>();
    private Random random = new Random();

    private String getRandomString(int k) {
        StringBuilder builder = new StringBuilder();
        while (k-- > 0) {
            builder.append(CS.charAt(random.nextInt(TOTAL_DIGITS)));
        }
        return builder.toString();
    }

    public String encode(String longUrl) {
        if (!StringUtils.hasLength(longUrl)) {
            return null;
        }
        if (reverseTinyUrlMap.containsKey(longUrl)) {
            return PREFIX + reverseTinyUrlMap.get(longUrl);
        } else {
            while (true) {
                String shortedUrl = getRandomString(DIGITS);
                if (tinyUrlMap.containsKey(shortedUrl)) {
                    continue;
                }
                tinyUrlMap.put(shortedUrl, longUrl);
                reverseTinyUrlMap.put(longUrl, shortedUrl);
                return PREFIX + shortedUrl;
            }
        }
    }

    public String decode(String shortUrl) {
        if (!StringUtils.hasLength(shortUrl)) {
            return null;
        }
        return tinyUrlMap.get(shortUrl.substring(PREFIX.length()));
    }
}

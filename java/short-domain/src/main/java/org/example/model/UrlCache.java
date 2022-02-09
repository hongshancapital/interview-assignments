package org.example.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UrlCache {
    private static Map<String, String> shortCodeAndLongUrlMap = new ConcurrentHashMap<>();

    private static Map<String, String> longUrlAndShortCodeMap = new ConcurrentHashMap<>();

    public static void add(String shortCode, String longUrl) {
        shortCodeAndLongUrlMap.put(shortCode, longUrl);
        longUrlAndShortCodeMap.put(longUrl, shortCode);
    }

    public static String getLongUrl(String shortCode) {
        return shortCodeAndLongUrlMap.get(shortCode);
    }

    public static void remove(String longUrl) {
        if (longUrlAndShortCodeMap.containsKey(longUrl)) {
            String shortCode = longUrlAndShortCodeMap.get(longUrl);
            longUrlAndShortCodeMap.remove(longUrl);
            shortCodeAndLongUrlMap.remove(shortCode);
        }
    }

    public static String getShortCode(String longUrl) {
        return longUrlAndShortCodeMap.get(longUrl);
    }
}

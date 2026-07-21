package com.yilong.shorturl.util;

public class UrlShorter {

    public static String encodeUrl(String url) {
        if (url == null || url.length() == 0) {
            throw new IllegalArgumentException("url must not be empty");
        }
        return Number62.encode(MurmurHasher.hash(url));
    }

    private UrlShorter(){}
}

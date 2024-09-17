package com.findme.url.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TransformUrl {
    Map<String, Long> urlMap = new HashMap<String, Long>();

    Long a = 0L;

    public  String longTransformShortUrl(String longUrl) {
        if (urlMap.containsKey(longUrl)) {
            return String.valueOf(urlMap.get(longUrl));
        } else {
            ++a;
            urlMap.put(longUrl, a);
            return String.valueOf(a);
        }

    }

    public  String shortTransformLongUrl(String shortUrl) {

        for (Map.Entry<String, Long> entry : urlMap.entrySet()) {
            if (String.valueOf(entry.getValue()).equals(shortUrl)) {
                return entry.getKey();
            }
        }
        return "找不到对于的长域名";
    }
}

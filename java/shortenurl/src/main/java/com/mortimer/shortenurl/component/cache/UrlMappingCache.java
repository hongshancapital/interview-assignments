package com.mortimer.shortenurl.component.cache;

public interface UrlMappingCache {
    String get(String key);

    void put(String key, String value);
}

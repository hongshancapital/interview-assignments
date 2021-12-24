package com.yuanyang.hongshan.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@Slf4j
public class UrlCacheUtil {

    @Value("${urlMap.maxSize}")
    private Integer URL_MAX_SIZE;

    private static LRUCache<String, String> urlCache;

    @PostConstruct
    public void init() {
        urlCache = new LRUCache<>(URL_MAX_SIZE);
    }

    public Boolean put(String key,String value) {
        urlCache.put(key,value);
        return true;
    }

    public Boolean remove(String key) {
        urlCache.remove(key);
        return true;
    }

    public String get(String key) {
        return urlCache.getOrDefault(key,"");
    }
}

class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private int capacity;

    public LRUCache(int capacity) {
        super(capacity, 0.75F, true);
        this.capacity = capacity;
    }

    @Override
    public boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;
    }
}


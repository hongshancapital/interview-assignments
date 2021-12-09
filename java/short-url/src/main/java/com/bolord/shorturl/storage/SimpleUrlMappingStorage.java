package com.bolord.shorturl.storage;

import javax.annotation.PostConstruct;

import com.bolord.shorturl.config.ShortUrlProperties;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * URL映射存储 - JVM内部实现
 *
 * @author alex
 */
public class SimpleUrlMappingStorage implements UrlMappingStorage {

    private Cache<String, String> cache;

    private ShortUrlProperties shortUrlProperties;

    public SimpleUrlMappingStorage(ShortUrlProperties shortUrlProperties) {
        this.shortUrlProperties = shortUrlProperties;
    }

    @PostConstruct
    public void init() {
        cache = Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(shortUrlProperties.getMaxCacheSize())
                .build();
    }

    @Override
    public String get(String id) {
        return cache.getIfPresent(id);
    }

    @Override
    public void set(String id, String url) {
        cache.put(id, url);
    }

    @Override
    public void remove(String id) {
        cache.asMap().remove(id);
    }

    @Override
    public int size() {
        return cache.asMap().size();
    }

}

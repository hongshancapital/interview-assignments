package com.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.service.MatchStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @Author jeffrey
 * @Date 2021/10/12
 * @description: 存储数据实现
 */
@Slf4j
@Component
public class MatchStorageServiceImpl implements MatchStorageService {

    public static final int MAX_CACHE_SIZE = 10000;
    public static final int MAX_CACHE_DAYS = 90;
    public static final int MAX_SUPPORT_URL_LENGTH = 1000;
    public static final String BASE_URL = "http://t.cn";

    private Cache<String, String> shortToLongCache = CacheBuilder.newBuilder()
            .expireAfterWrite(MAX_CACHE_DAYS, TimeUnit.DAYS)
            .maximumSize(MAX_CACHE_SIZE)
            .build();

    private Cache<String, String> longToShortCache = CacheBuilder.newBuilder()
            .expireAfterWrite(MAX_CACHE_DAYS, TimeUnit.DAYS)
            .maximumSize(MAX_CACHE_SIZE)
            .build();
    @Override
    public Optional<String> getShortUrlByLongUrl(String longUrl) {
        return Optional.ofNullable(longToShortCache.getIfPresent(longUrl));
    }

    @Override
    public Optional<String> getLongUrlByShortUrl(String shortUrl) {
        return Optional.ofNullable(shortToLongCache.getIfPresent(shortUrl));
    }

    @Override
    public void setUrlMatch(String longUrl, String shortUrl) {
        shortToLongCache.put(shortUrl, longUrl);
        longToShortCache.put(longUrl, shortUrl);
    }

    @Override
    public long getCacheSize() {
        return shortToLongCache.size();
    }
}

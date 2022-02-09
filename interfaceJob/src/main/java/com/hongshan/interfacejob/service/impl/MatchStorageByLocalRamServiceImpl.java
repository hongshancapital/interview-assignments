package com.hongshan.interfacejob.service.impl;

import com.hongshan.interfacejob.constant.CommonConstants;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.hongshan.interfacejob.service.MatchStorageService;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

/**
 * 信息存储
 * */
@Component
public class MatchStorageByLocalRamServiceImpl implements MatchStorageService {

    /**
     * 短域名映射长域名
     * 失效时间
     * */
    private Cache<String, String> shortToLongCache = CacheBuilder.newBuilder()
            .expireAfterWrite(CommonConstants.MAX_CACHE_DAYS, TimeUnit.DAYS)
            .maximumSize(CommonConstants.MAX_CACHE_SIZE)
            .build();

    /**
     * 长域名映射短域名
     * 失效时间
     * */
    private Cache<String, String> longToShortCache = CacheBuilder.newBuilder()
            .expireAfterWrite(CommonConstants.MAX_CACHE_DAYS, TimeUnit.DAYS)
            .maximumSize(CommonConstants.MAX_CACHE_SIZE)
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
    public void setUrlMatch(String shortUrl,String longUrl) {
        shortToLongCache.put(shortUrl, longUrl);
        longToShortCache.put(longUrl, shortUrl);
    }

    @Override
    public long getCacheSize() {
        return shortToLongCache.size();
    }
}

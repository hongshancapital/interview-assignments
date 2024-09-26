package com.example.shorturl.manager;

import com.example.shorturl.util.Base62Util;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;


/**
 * @author yingchen
 * @date 2022/3/15
 */
@Component
public class ShortUrlManager {
    private AtomicLong id = new AtomicLong(1);

    /**
     * 以短域名为key的，原始域名为value的缓存对象，用来通过短域名获取原始域名
     */
    public static final Cache<String, String> shortUrlAsKeyCache = CacheBuilder.newBuilder()
            .initialCapacity(100)
            .maximumSize(1024 * 1024)
            // 暂定过期时间为7天
            .expireAfterWrite(7 * 24 * 60 * 60, TimeUnit.SECONDS)
            .build();

    /**
     * 以原始域名为key的，短域名为value的缓存对象，获取短域名时起到缓存作用，避免每次请求都需要重新生成短域名
     */
    public static final Cache<String, String> originalUrlAsKeyCache = CacheBuilder.newBuilder()
            .initialCapacity(100)
            .maximumSize(2 * 1000 * 1000)
            // 暂定过期时间为7天
            .expireAfterWrite(7 * 24 * 60 * 60, TimeUnit.SECONDS)
            .build();


    public String getShortUrl(String originalUrl) {
        final String cacheShortUrl = originalUrlAsKeyCache.getIfPresent(originalUrl);

        if (cacheShortUrl != null) {
            return cacheShortUrl;
        }
        final String shortUrl = Base62Util.base10ToBase62(id.getAndIncrement());
        shortUrlAsKeyCache.put(shortUrl, originalUrl);
        originalUrlAsKeyCache.put(originalUrl, shortUrl);
        return shortUrl;
    }

    public String getOriginalUrl(String shortUrl) {
        return shortUrlAsKeyCache.getIfPresent(shortUrl);
    }
}

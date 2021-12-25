package com.sequoia.domain.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.sequoia.domain.service.IUrlCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class UrlCacheServiceImpl implements IUrlCacheService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UrlCacheServiceImpl.class);

    public final Cache<Long, String> URL_CACHE;

    public UrlCacheServiceImpl(@Value("${app.cache.init_capacity}") int initCapacity, @Value("${app.cache.max_capacity}") long maxCapacity) {
        URL_CACHE = CacheBuilder.newBuilder()
                .concurrencyLevel(8)
                .initialCapacity(initCapacity)
                .maximumSize(maxCapacity)
                .expireAfterAccess(24, TimeUnit.HOURS)
                .removalListener(notification -> {
                    LOGGER.info("Key {} removed, cause: {}", notification.getKey(), notification.getCause());
                })
                .build();
    }

    public void put(long id, String url) {
        URL_CACHE.put(id, url);
    }

    public String get(long id) {
        return URL_CACHE.getIfPresent(id);
    }
}

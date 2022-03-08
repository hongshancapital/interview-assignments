package com.sequoiacap.tinyurl.repository.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.sequoiacap.tinyurl.repository.TinyUrlDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 *
 */
@Repository
public class MemoryTinyUrlDao implements TinyUrlDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemoryTinyUrlDao.class);

    @Value("${memory.cache.concurrencyLevel:10}")
    private int concurrencyLevel;

    @Value("${memory.cache.recordStats:false}")
    private boolean isRecordStats;
    @Value("${memory.cache.initialCapacity:1000}")
    private int initialCapacity;
    @Value("${memory.cache.maximumSize:100000}")
    private int maximumSize;
    @Value("${memory.cache.expireSeconds:600}")
    private int expireSeconds;


    private Cache<String, String> cache;

    @PostConstruct
    public void init() {
        CacheBuilder<String, String> cacheBuilder = CacheBuilder.newBuilder()
                .concurrencyLevel(concurrencyLevel)
                .initialCapacity(initialCapacity)
                .maximumSize(maximumSize)
                .expireAfterWrite(expireSeconds, TimeUnit.SECONDS)
                .removalListener(notification -> {
                    LOGGER.info("{}:{}. ({})", notification.getKey(), notification.getValue(), notification.getCause());
                });
        if (isRecordStats) {
            cacheBuilder.recordStats();
        }
        cache = cacheBuilder.build();
    }

    @Override
    public synchronized void save(String url, String tinyUrl) {
        cache.put(tinyUrl, url);
    }

    @Override
    public Optional<String> queryUrl(String tinyUrl) {
        return Optional.ofNullable(cache.getIfPresent(tinyUrl));
    }

}

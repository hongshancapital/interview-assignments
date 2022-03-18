package com.yofei.shortlink.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Value;
import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class CacheService {

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
                                                                    log.info("{}:{}. ({})", notification.getKey(), notification.getValue(), notification.getCause());
                                                                });
        if (isRecordStats) {
            cacheBuilder.recordStats();
        }
        cache = cacheBuilder.build();
    }

    public void setUrl(String code,String url) {
        cache.put(code,url);
    }

    public String getUrl(String code) {
        return cache.getIfPresent(code);
    }

}

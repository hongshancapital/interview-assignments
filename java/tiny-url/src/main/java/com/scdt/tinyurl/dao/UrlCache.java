package com.scdt.tinyurl.dao;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.Scheduler;
import com.scdt.tinyurl.config.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.concurrent.Executors;

@Slf4j
@Repository
@ConditionalOnProperty(name = "application.cache.enable", havingValue = "true")
public class UrlCache {

    @Autowired
    private AppConfig appConfig;

    private Cache<String,String> lruCache;

    @PostConstruct
    public void init() {
        lruCache = Caffeine.newBuilder()
                //最大容量
                .maximumSize(appConfig.getCachePercentage().multiply(new BigDecimal(appConfig.getMaxCapacity())).longValue())
                //设置定时任务线程池，removalListener为懒移除，当长时间没有访问操作时不会自动移除并且回调过期函数，设置多线程任务防止单个定时任务阻塞
                .scheduler(Scheduler.forScheduledExecutorService(Executors.newScheduledThreadPool(appConfig.getCacheSchedulerThreadSize())))
                //设置访问后过期时间
                .expireAfterWrite(appConfig.getCacheExpiredDuration(), appConfig.getExpireTimeUnit(appConfig.getCacheExpiredTimeunit()))
                //设置过期时间监听器 这里只需打印过期的key作为日志记录
                .removalListener((String key, String value, RemovalCause cause) -> {
                    log.info("cache 已过期,tiny url:{}, original url: {},reason: {}",key,value,cause);
                })
                .build();
    }



    public void put(String longUrl,String tinyUrl) {
        lruCache.put(longUrl,tinyUrl);
    }

    public String get(String tinyUrl) {
        return lruCache.getIfPresent(tinyUrl);
    }

}

package com.scdt.tinyurl.dao;


import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.Scheduler;
import com.scdt.tinyurl.config.AppConfig;
import com.scdt.tinyurl.model.ExpiredEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;


@Slf4j
@Repository
public class UrlStorage {

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private ApplicationEventPublisher eventPublisher;


    private Cache<String,String> storage;


    @PostConstruct
    public void init() {
        storage = Caffeine.newBuilder()
                //最大容量
                .maximumSize(appConfig.getMaxCapacity())
                //设置定时任务线程池，removalListener为懒移除，当长时间没有访问操作时不会自动移除并且回调过期函数，设置多线程任务防止单个定时任务阻塞
                .scheduler(Scheduler.forScheduledExecutorService(Executors.newScheduledThreadPool(appConfig.getStorageSchedulerThreadSize())))
                //设置访问后过期时间
                .expireAfterWrite(appConfig.getStorageExpiredDuration(), appConfig.getExpireTimeUnit(appConfig.getStorageExpiredTimeunit()))
                //设置过期时间监听器 发布过期事件 通知SequenceManager减小已使用ID(currentCount)的数量
                .removalListener((String key, String value, RemovalCause cause) -> {
                    log.info("url已过期,tiny url:{}, original url: {},reason: {}",key,value,cause);
                    eventPublisher.publishEvent(new ExpiredEvent(key));
                })
                .build();
    }

    public void put(String tinyUrl,String longUrl) {
        storage.put(tinyUrl,longUrl);
        log.debug("put offset: {},map: {}",tinyUrl,storage.asMap());
    }

    public String get(String tinyUrl) {
        log.info(storage.asMap().toString());
        return storage.getIfPresent(tinyUrl);
    }


}

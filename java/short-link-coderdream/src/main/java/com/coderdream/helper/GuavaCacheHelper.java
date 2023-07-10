package com.coderdream.helper;

import com.coderdream.utils.Config;
import com.coderdream.utils.Constants;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Guava 缓存辅助类
 *
 * @author CoderDream
 * @version 1.0
 * @date 2022/5/8
 */
@Service
@Slf4j
public class GuavaCacheHelper {
    @Resource
    private Config config;

    private Cache<Object, Object> cache;

    @PostConstruct
    public void init() {
        cache = CacheBuilder.newBuilder()
                // 设置并发级别为8，并发级别是指可以同时写缓存的线程数，此处最好和cpu数一致（Runtime.getRuntime().availableProcessors()）
                .concurrencyLevel(Constants.CONCURRENCY_LEVEL)
                // 设置缓存容器的初始容量为10
                .initialCapacity(Constants.INITIAL_CAPACITY)
                // 设置缓存最大容量为100，超过100之后就会按照LRU最近最少使用算法来移除缓存项，生产环境可设置为 100000000
                .maximumSize(Constants.MAXIMUM_SIZE)
                // 是否需要统计缓存情况，该操作消耗一定的性能，生产环境应该去除
                .recordStats()
                // 设置写缓存后n秒钟过期
                .expireAfterWrite(config.EXPIRE_SEC, TimeUnit.SECONDS)
                // 设置读写缓存后n秒钟过期，实际很少用到，类似于expireAfterWrite
                //.expireAfterAccess(17, TimeUnit.SECONDS)
                // 只阻塞当前数据加载线程，其他线程返回旧值
                //.refreshAfterWrite(13, TimeUnit.SECONDS)
                // 设置缓存的移除通知
                .removalListener(notification -> {
                    log.error(notification.getKey() + " " + notification.getValue() + " 被移除,原因:" + notification.getCause());
                })
                //build方法中可以指定CacheLoader，在缓存不存在时通过CacheLoader的实现自动加载缓存
                .build();
    }

    /**
     * 获取缓存
     */
    public Object get(String key) {
        return StringUtils.isEmpty(key) ? null : cache.getIfPresent(key);
    }

    /**
     * 放入缓存
     */
    public void put(String key, Object value) {
        if (StringUtils.isNotEmpty(key) && value != null) {
            cache.put(key, value);
        } else {
            return;
        }
    }
}
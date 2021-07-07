package com.shorturl.service;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.shorturl.common.Constant.MAX_CACHE_CAPACITY;
import static com.shorturl.common.Constant.MAX_CACHE_EXPIRE_DAYS;

@Service
public class GuavaCacheService {

    static LoadingCache<String, String> cache = CacheBuilder.newBuilder()
            //设置并发级别为8，并发级别是指可以同时写缓存的线程数
            .concurrencyLevel(8)
            //设置缓存容器的初始容量为10
            .initialCapacity(10)
            //设置缓存最大容量为MAX_CACHE_CAPACITY，超过MAX_CACHE_CAPACITY之后就会按照LRU最近虽少使用算法来移除缓存项
            .maximumSize(MAX_CACHE_CAPACITY)
            //是否需要统计缓存情况,该操作消耗一定的性能,生产环境应该去除
            .recordStats()
            //设置写缓存后n秒钟过期
            .expireAfterWrite(MAX_CACHE_EXPIRE_DAYS, TimeUnit.DAYS)
            //只阻塞当前数据加载线程，其他线程返回旧值
            .refreshAfterWrite(MAX_CACHE_EXPIRE_DAYS, TimeUnit.DAYS).build(new CacheLoader<String, String>() {
                @Override
                public String load(String name) throws Exception {
                    return null;
                }
            });
    private static Logger logger = LoggerFactory.getLogger(GuavaCacheService.class);

    public Boolean put(String key, String value) {
        try {
            cache.put(key, value);
            return Boolean.TRUE;
        } catch (Exception e) {
            logger.error("写入失败原因->{}", e);
            return Boolean.FALSE;
        }
    }

    public String get(String key) {
        try {
            String value = cache.get(key);
            return value;
        } catch (Exception e) {
            logger.error("查询失败，原因->{}", e);
            return null;
        }
    }

    public long size() {
        return cache.size();
    }


}

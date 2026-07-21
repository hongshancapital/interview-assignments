package com.zoujing.shortlink.service;

import com.github.benmanes.caffeine.cache.*;
import com.zoujing.shortlink.model.ShortLinkAppConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Service("CacheStoreService")
@Slf4j
public class CacheStoreServiceImpl implements CacheStoreService {

    @Autowired
    private ShortLinkAppConfig appConfig;

    private Cache<String, Object> cache;

    /**
     * Cache.stats() 方法返回提供统计信息的CacheStats，如：
     * hitRate()：返回命中与请求的比率
     * hitCount(): 返回命中缓存的总数
     * evictionCount()：缓存逐出的数量
     * averageLoadPenalty()：加载新值所花费的平均时间
     */
    @PostConstruct
    public void initCache() {
        cache = Caffeine.newBuilder()
                .initialCapacity(10000) //初始缓存长度为100
                .maximumSize(appConfig.cacheMaxSize)  //最大长度
                .recordStats()     //可以打开统计信息收集
                .expireAfterWrite(appConfig.expire, TimeUnit.SECONDS)//设置缓存策略在1小时未写入过期缓存
                .build();
    }

    /**
     * 获取缓存
     */
    @Override
    public Object get(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return cache.getIfPresent(key);
    }

    /**
     * 存储缓存
     */
    @Override
    public void put(String key, Object value) {
        if (StringUtils.isEmpty(key) || value == null) {
            return;
        }
        cache.put(key, value);
    }

    @Override
    public void save(String shortLink, String longLink) {
        put(shortLink, longLink);
        put(longLink, shortLink);
    }
}

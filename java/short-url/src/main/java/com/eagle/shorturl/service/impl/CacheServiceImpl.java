package com.eagle.shorturl.service.impl;

import com.eagle.shorturl.service.CacheService;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.constructs.blocking.BlockingCache;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author eagle
 * @description
 */
@Slf4j
@Service
@Validated
public class CacheServiceImpl implements CacheService {

    /**
     * 初始化一级缓存，容量:1w，过期时间:10分钟
     */
    private final LoadingCache<String, String> l1Cache = Caffeine.newBuilder()
            .maximumSize(10_000)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build(key -> getFromL2Cache(key));

    /**
     * 初始化二级缓存，配置参见ehcache.xml
     */
    URL l2CacheConfig = this.getClass().getResource("/ehcache.xml");
    private final BlockingCache l2Cache = new BlockingCache(CacheManager.create(l2CacheConfig).getCache("l2cache"));

    @Override
    public boolean contain(@NotBlank String key) {
        boolean result = l2Cache.isKeyInCache(key);
        log.info("cache contain key:{} result:{}", key, result);
        return result;
    }

    @Override
    public String get(@NotBlank String key) {
        return getFromL1Cache(key);
    }

    @Override
    public void put(@NotBlank String key, @NotBlank String value) {
        putIntoL2Cache(key, value);
    }

    private String getFromL1Cache(@NotBlank String key) {
        String result = l1Cache.get(key);
        log.info("l1Cache get key:{} result:{}", key, result);
        return result;
    }

    private String getFromL2Cache(@NotBlank String key) {
        if (!l2Cache.isKeyInCache(key)) {
            return null;
        }
        Element element = l2Cache.get(key);
        log.info("l2Cache get key:{} result:{}", key, element);
        if (Objects.isNull(element)) {
            return null;
        }
        Object value = element.getObjectValue();
        if (Objects.isNull(value)) {
            return null;
        }
        return value.toString();
    }

    private void putIntoL2Cache(@NotBlank String key, @NotBlank String value) {
        l2Cache.put(new Element(key, value), false);
        log.info("l2Cache put key:{} value:{}", key, value);
    }

}

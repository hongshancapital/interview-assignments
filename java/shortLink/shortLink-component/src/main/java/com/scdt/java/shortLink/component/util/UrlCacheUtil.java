package com.scdt.java.shortLink.component.util;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.scdt.java.shortLink.component.config.UrlCacheConfig;
import com.scdt.java.shortLink.component.constant.ServiceException;

import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UrlCacheUtil {

    @Resource
    private UrlCacheConfig urlCacheConfig;

    private LoadingCache<String, String> long2ShortCache;
    private LoadingCache<String, String> short2LongCache;

    @PostConstruct
    public void init() {
        long2ShortCache = CacheBuilder.newBuilder()
                .initialCapacity(urlCacheConfig.getInitialCapacity()).maximumSize(urlCacheConfig.getMaximumSize())
                .expireAfterAccess(1, TimeUnit.DAYS).build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        return "";
                    }
                });
        short2LongCache = CacheBuilder.newBuilder()
                .initialCapacity(urlCacheConfig.getInitialCapacity()).maximumSize(urlCacheConfig.getMaximumSize())
                .expireAfterAccess(1, TimeUnit.DAYS).build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        return "";
                    }
                });
    }

    public synchronized void saveLinkRelation(String key, String val) {
        try {
            long2ShortCache.put(key, val);
            short2LongCache.put(val, key);
        } catch (Exception e) {
            long2ShortCache.invalidate(key);
            short2LongCache.invalidate(val);
            log.error("保存缓存出错,key:{}", key, e);
            throw new ServiceException("生成短链接失败，请稍后重试");
        }
    }

    public String getShortFromLong(String key) {
        try {
            return long2ShortCache.get(key);
        } catch (Exception e) {
            log.error("获取缓存出错,key:{}", key, e);
        }
        return "";
    }

    public String getLongFromShort(String key) {
        try {
            return short2LongCache.get(key);
        } catch (Exception e) {
            log.error("获取缓存出错,key:{}", key, e);
        }
        return "";
    }
}

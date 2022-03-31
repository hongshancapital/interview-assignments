package org.example.shorturl.util;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import lombok.extern.slf4j.Slf4j;
import org.example.shorturl.properties.CacheProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * url缓存工具
 *
 * @author bai
 * @date 2022/3/19 21:30
 */
@Slf4j
@Component
public class UrlCacheUtil {
    
    /** 过期时间 */
    private static long timeout;
    /** 容量 */
    private static Integer capacity;
    /** 短url缓存 */
    private static volatile Cache<String, String> shortUrlCache;
    /** 长url缓存 */
    private static volatile Cache<String, String> longUrlCache;
    @Resource
    private CacheProperty cacheProperty;
    
    /**
     * 初始化
     */
    @PostConstruct
    public void init() {
        timeout = cacheProperty.getTimeout();
        capacity = cacheProperty.getCapacity();
    }
    
    /**
     * 获取短url cache实例
     *
     * @return {@link Cache}<{@link String}, {@link String}>
     */
    public static Cache<String, String> shortUrlInstance() {
        if (shortUrlCache == null) {
            synchronized (UrlCacheUtil.class) {
                if (shortUrlCache == null) {
                    shortUrlCache = CacheUtil.newLFUCache(capacity, timeout);
                }
            }
        }
        return shortUrlCache;
    }
    
    /**
     * 获取长url实例
     * 获取长url cache实例
     *
     * @return {@link Cache}<{@link String}, {@link String}>
     */
    public static Cache<String, String> longUrlInstance() {
        
        if (longUrlCache == null) {
            synchronized (UrlCacheUtil.class) {
                if (longUrlCache == null) {
                    longUrlCache = CacheUtil.newLFUCache(capacity, timeout);
                }
            }
        }
        return longUrlCache;
    }
}

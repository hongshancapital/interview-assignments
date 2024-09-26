package com.wenchao.jacoco.demo.utils;

import cn.hutool.cache.impl.LRUCache;
import cn.hutool.core.lang.func.Func0;

/**
 * 缓存 LRU 实现
 * 1、缓存中保存 一定固定数量值
 * 2、同一条数据缓存两次 通过长链接获取短链接、通过短链接获取长链接
 *
 * @author Wenchao Gong
 * @date 2021/12/15 18:31
 */
public class CacheUtils {

    /**
     * 缓存最大长度
     */
    public static final int CAPACITY = 10000;

    /**
     * KEY 为长链接
     */
    public static final LRUCache<String, Long> CACHE_LONG = new LRUCache<>(CAPACITY);


    /**
     * KEY 为短链接
     */
    public static final LRUCache<Long, String> CACHE_SHORT = new LRUCache<>(CAPACITY);

    /**
     * 存放时 同时存放入两个缓存中
     *
     * @param longUrl
     * @param shortUrl
     */
    public static void put(String longUrl, Long shortUrl) {
        CACHE_LONG.put(longUrl, shortUrl);
        CACHE_SHORT.put(shortUrl, longUrl);
    }

    /**
     * 通过短URl 获取长 URL
     *
     * @param shortUrl
     * @param supplier
     * @return
     */
    public static String getLongUrl(Long shortUrl, Func0<String> supplier) {
        return CACHE_SHORT.get(shortUrl, supplier);
    }

    /**
     * 通过长URl 获取短 URL
     *
     * @param longUrl
     * @param supplier
     * @return
     */
    public static Long getShortUrl(String longUrl, Func0<Long> supplier) {
        return CACHE_LONG.get(longUrl, supplier);
    }
}

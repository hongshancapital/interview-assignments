package com.scdt.domain.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;


public class CacheUtil {

    /**
     * 使用缓存，如果内存不够会基于内存容量尝试回收最近没有使用或总体上很少使用的缓存项
     */
    private static Cache<String,String> shortToLongCache = CacheBuilder.newBuilder().build();
    private static Cache<String,String> longToShortCache = CacheBuilder.newBuilder().build();

    public static void putShortToLong(String key,String value){
        shortToLongCache.put(key,value);
    }

    public static void putLongToShort(String key,String value){
        longToShortCache.put(key,value);
    }

    public static String getShortToLong(String key) {
        return shortToLongCache.getIfPresent(key);
    }

    public static String getLongToShort(String key) {
        return longToShortCache.getIfPresent(key);
    }
}

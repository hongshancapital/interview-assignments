package com.assignment.cache;

import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地缓存
 *
 * @author shifeng
 */
public class UrlCache {
    /**
     * 域名缓存map
     */
    static SoftReference<ConcurrentHashMap<String, String>> shortCache = new SoftReference(new ConcurrentHashMap<>(16));


    /**
     * 添加缓存数据
     *
     * @param key
     * @param val
     */
    public static void put(String key, String val) {
        shortCache.get().put(key, val);
    }

    /**
     * 获取url
     *
     * @param key
     * @return
     */
    public static String get(String key) {
        return shortCache.get().get(key);
    }

    /**
     * 清空cache中的数据
     */
    public static void clearCache(){
        shortCache.get().clear();
    }
}

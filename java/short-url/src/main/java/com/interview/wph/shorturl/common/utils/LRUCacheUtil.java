package com.interview.wph.shorturl.common.utils;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import com.interview.wph.shorturl.common.consts.SpringConst;

public class LRUCacheUtil {
    //定义LRU缓存, 以防OOM,存放已经有的,不需提供删除
    private static Cache<Long, String> validCache;
    //定义LRU缓存, 以防OOM,存放空值
    private static Cache<Long, String> invalidCache;


    static {
        SpringConst sConst = SpringUtil.getBean(SpringConst.class);
        validCache = CacheUtil.newLRUCache(sConst.getValidCacheSize());
        invalidCache = CacheUtil.newLRUCache(sConst.getInvalidCacheSize());
    }


    /**
     * 存放有效的值
     *
     * @param key   key
     * @param value value
     */
    public static void putValid(Long key, String value) {
        validCache.put(key, value);
    }

    /**
     * 获取有效的值
     *
     * @param key key
     */
    public static String getValid(Long key) {
        return validCache.get(key);
    }


    /**
     * 存放无效的值
     *
     * @param key key
     */
    public static void putInvalid(Long key) {
        invalidCache.put(key, null);
    }

    /**
     * 是否包含无效值
     *
     * @param key key
     */
    public static Boolean isInvalid(Long key) {
        return invalidCache.containsKey(key);
    }

    /**
     * 删除无效值
     *
     * @param key key
     */
    public static void removeInvalid(Long key) {
        invalidCache.remove(key);
    }
}

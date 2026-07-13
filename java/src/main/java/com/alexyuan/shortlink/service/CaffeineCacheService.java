package com.alexyuan.shortlink.service;

import com.alexyuan.shortlink.common.variant.CacheVariant;

public interface CaffeineCacheService {

    /**
     * @Description     cache的标准写入方法
     */
    CacheVariant cachePut(String key, CacheVariant result);

    /**
     * @Description     cache的标准写入方法
     */
    CacheVariant cacheGet(String key);

    /**
     * @Description     cache的标准更新方法
     */
    CacheVariant cacheUpdate(CacheVariant result);

    /**
     * @Description     cache的标准删除方法
     */
    CacheVariant cacheDelete(String key);

}

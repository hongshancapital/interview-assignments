package com.zoujing.shortlink.service;

public interface CacheStoreService {

    /**
     * 获取缓存
     *
     * @param key
     * @return Object
     */
    Object get(String key);

    /**
     * 添加一个缓存
     *
     * @param key
     * @param value
     */
    void put(String key, Object value);

    /**
     * 存储短链接链接
     *
     * @param shortLink
     * @param longLink
     */
    void save(String shortLink, String longLink);
}

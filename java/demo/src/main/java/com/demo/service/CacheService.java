package com.demo.service;

/**
 * @author syd
 * @description
 * @date 2022/1/12
 */
public interface CacheService {
    /**
     * 获取缓存
     *
     * @param key
     * @return
     */
    String get(String key);

    /**
     * 缓存数据
     *
     * @param key
     * @param value
     */
    void set(String key, String value);
}

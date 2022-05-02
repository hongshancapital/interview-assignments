package com.scdt.shortlink.core.cache;

/**
 * 本地二级缓存接口
 *
 * @Author tzf
 * @Date 2022/4/29
 */
public interface ICache {
    /**
     * 获取缓存值
     *
     * @param key 存储key
     * @return
     */
    String get(String key);

    /**
     * 按照kv结构放入缓存
     *
     * @param key     键
     * @param value   值
     * @return
     */
    void put(String key, String value);
}

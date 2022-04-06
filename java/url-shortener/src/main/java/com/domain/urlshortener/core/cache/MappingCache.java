package com.domain.urlshortener.core.cache;

/**
 * @author: rocky.hu
 * @date: 2022/4/6 8:02
 */
public interface MappingCache {

    /**
     * 存储映射关系
     *
     * @param key
     *          键
     * @param value
     *          值
     */
    void put(String key, String value);

    /**
     * 获取值
     *
     * @param key
     *          键
     * @return
     *          值
     */
    String get(String key);

}

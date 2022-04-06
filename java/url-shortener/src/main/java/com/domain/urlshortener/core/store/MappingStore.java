package com.domain.urlshortener.core.store;

/**
 * @author: rocky.hu
 * @date: 2022/4/5 23:06
 */
public interface MappingStore {

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

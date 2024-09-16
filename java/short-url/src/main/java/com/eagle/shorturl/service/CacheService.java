package com.eagle.shorturl.service;

import javax.validation.constraints.NotBlank;

/**
 * @author eagle
 * @description
 */
public interface CacheService {

    /**
     * 是否包含key
     * @param key
     * @return
     */
    boolean contain(@NotBlank String key);

    /**
     * 获取key的value
     * @param key
     * @return
     */
    String get(@NotBlank String key);

    /**
     * 存储key和value
     * @param key
     * @param value
     */
    void put(@NotBlank String key, @NotBlank String value);
}

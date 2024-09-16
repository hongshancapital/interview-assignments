package com.eagle.shorturl.service;

import javax.validation.constraints.NotBlank;

/**
 * @author eagle
 * @description
 */
public interface BloomFilterService {

    /**
     * 判断布隆过滤器是否包含
     * @param key
     * @return
     */
    boolean mightContain(@NotBlank String key);

    /**
     * 向布隆过滤器新增数据
     * @param key
     * @return
     */
    boolean put(@NotBlank String key);

}

package com.domain.urlshortener.core.cache;

import lombok.Getter;

/**
 * @author: rocky.hu
 * @date: 2022/4/6 8:24
 */
@Getter
public class MappingCacheEntry {

    private String key;
    private String value;

    public MappingCacheEntry(String key, String value) {
        this.key = key;
        this.value = value;
    }

}

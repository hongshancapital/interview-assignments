package com.shortlink.common;

import com.google.common.cache.CacheLoader;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyCacheLoader extends CacheLoader<String, String> {
    @Override
    public String load(String key) throws Exception {
        log.debug("查询失败，key: " + key);
        return "";
    }
}

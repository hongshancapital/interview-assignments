package com.shortlink.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class ShortLinkCache {

    private LoadingCache<String, String> cache;

    public LoadingCache<String, String> getCache(){
        return cache;
    }

    @PostConstruct
    public void init(){
        // 使用1/3的内存用于短域名查询缓存
        long maxSize = Runtime.getRuntime().totalMemory() / 3 / Constants.LONG_LINK_MAX_SIZE;
        cache  = CacheBuilder.newBuilder().recordStats()
                .maximumSize(maxSize).build(new MyCacheLoader());
    }
}

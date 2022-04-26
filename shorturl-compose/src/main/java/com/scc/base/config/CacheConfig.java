package com.scc.base.config;

import com.scc.base.cache.LfuCache;
import com.scc.base.cache.LruCache;
import com.scc.base.constant.CacheConstants;
import com.scc.base.service.Cache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author renyunyi
 * @date 2022/4/24 5:20 下午
 * @description project all config is here
 **/
@Configuration
public class CacheConfig {

    /**
     * cache type
     */
    @Value("${shorturl.cache.type}")
    private String shortUrlCacheType;

    /**
     * cache capacity
     */
    @Value("${shorturl.cache.capacity}")
    private int shortUrlCacheCapacity;

    @Bean(name = "shortToLongCache")
    public Cache<String, String> getShortToLongCache() throws Exception {
        return getCache();
    }

    @Bean(name = "longToShortCache")
    public Cache<String, String> getLongToShortCache() throws Exception {
        return getCache();
    }

    private Cache<String, String> getCache() throws Exception{

        // to simplify check capacity when use cache
        if (CacheConstants.MIN_CACHE_SIZE > this.shortUrlCacheCapacity){
            throw new Exception("cache min size should be bigger than " + CacheConstants.MIN_CACHE_SIZE);
        }

        if (StringUtils.equals(CacheConstants.LRU, shortUrlCacheType)){
            return new LruCache<>(this.shortUrlCacheCapacity);
        }

        if (StringUtils.equals(CacheConstants.LFU, shortUrlCacheType)){
            return new LfuCache<>(this.shortUrlCacheCapacity);
        }

        throw new Exception("cache not config!");
    }

}

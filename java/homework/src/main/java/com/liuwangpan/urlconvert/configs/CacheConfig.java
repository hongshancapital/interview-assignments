package com.liuwangpan.urlconvert.configs;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * local cacheMange config
 * @author wp_li
 **/
@Configuration
@Slf4j
public class CacheConfig {

    //initialCapacity
    @Value("${localCache.initialCapacity:100000}")
    private Integer initialCapacity;
    //maximum capacity  cause timeout beginning lru recover 1024*10000
    @Value("${localCache.maximumSize:5000000}")
    private Integer maximumSize;
    // validity 30 days
    @Value("${localCache.expire:30}")
    private Integer expireDay;


    /**
     *  config CacheManager
     */
    @Bean(name = "caffeineCacheManager")
    public CacheManager cacheManagerWithCaffeine() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        Caffeine caffeine = Caffeine.newBuilder()
                .expireAfterAccess(expireDay, TimeUnit.DAYS)
                .initialCapacity(initialCapacity)
                .removalListener((RemovalListener<String, String>) (key, value, cause)
                        -> log.info("lru recover key started:" + key + ", value:" + value + ", delete reason:" + cause.toString()))
                .maximumSize(maximumSize);
        cacheManager.setCaffeine(caffeine);
        cacheManager.setAllowNullValues(false);
        return cacheManager;
    }

}
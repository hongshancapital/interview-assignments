package com.scdt.job.lsx.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author lsx
 */
@Configuration
public class CacheConfig {
    @Bean
    public Cache<String, String> getCache() {
        return CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.HOURS)
                .initialCapacity(1024)
                .maximumSize(1000000)
                .build();
    }
}

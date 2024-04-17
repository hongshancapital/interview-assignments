package com.david.urlconverter.service.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * 基于Caffeine缓存构架的内存存储 长短域名映射
 * @author whohasthis
 */
@Configuration
public class StorageCacheConfig {

    @Value("${caffeine.config.expireAfterAccessMinutesShortToLong}")
    private Long expireAfterAccessShortToLong;
    @Value("${caffeine.config.expireAfterAccessMinutesLongToShort}")
    private Long expireAfterAccessLongToShort;
    @Value("${caffeine.config.initSize}")
    private Integer initSize;

    @Bean(name="shortToLongCaffeineCache")
    public Cache<String, String> shortToLongCaffeineCache() {
        return Caffeine.newBuilder()
                .expireAfterAccess(expireAfterAccessShortToLong, TimeUnit.MINUTES)
                .initialCapacity(initSize)
                .build();
    }

    @Bean(name="longToShortCaffeineCache")
    public Cache<String, String> longToShortCaffeineCache() {
        return Caffeine.newBuilder()
                .expireAfterAccess(expireAfterAccessLongToShort, TimeUnit.MINUTES)
                .initialCapacity(initSize)
                .build();
    }
}

package com.url.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.TimeUnit;

/**
 * 缓存配置类
 * @Author jeckzeng
 * @Date 2022/4/30
 * @Version 1.0
 */
@Slf4j
@Configuration
public class UrlCacheConfig {

    @Value("${cache.maximumSize}")
    private long maximumSize;

    @Value(("${cache.expireTime}"))
    private long expireTime;

    @Bean
    public Cache<String,String> createUrlCache(){
        return Caffeine.newBuilder()
                .maximumSize(maximumSize)
                .expireAfterAccess(expireTime,TimeUnit.MINUTES)
                .build();
    }
}

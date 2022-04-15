package com.scdt.china.shorturl.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;


/**
 * Cache配置
 *
 * @author：costa
 * @date：Created in 2022/4/11 17:45
 */
@Configuration
@EnableCaching
public class CaffeineCacheConfig {

    @Bean
    public Cache<String, Object> caffeineCache() {
        return Caffeine.newBuilder()
                // 设置90天过期
                .expireAfterWrite(90, TimeUnit.DAYS)
                // 初始的缓存空间大小
                .initialCapacity(1000)
                // 缓存的最大条数
                .maximumSize(100000L)
                .build();
    }
}

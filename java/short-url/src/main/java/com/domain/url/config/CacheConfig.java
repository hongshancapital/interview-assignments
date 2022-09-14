package com.domain.url.config;

import com.domain.url.cache.LRUCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {
    @Value("${lru.cache.capacity}")
    private Integer capacity;

    @Bean
    public LRUCache lruCache() {
        return new LRUCache(this.capacity);
    }
}

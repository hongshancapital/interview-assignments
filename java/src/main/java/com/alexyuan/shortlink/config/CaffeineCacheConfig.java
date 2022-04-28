package com.alexyuan.shortlink.config;

import com.github.benmanes.caffeine.cache.*;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import com.alexyuan.shortlink.config.SystemConfig;
import org.springframework.lang.NonNull;

import javax.annotation.Nullable;

@Configuration
public class CaffeineCacheConfig {

    private static final Logger logger = LoggerFactory.getLogger(CaffeineCacheConfig.class);

    @Autowired
    private SystemConfig config;
    @Bean
    public Cache<String, Object> caffeineCache() {
        return Caffeine.newBuilder()
                // 初始的缓存空间大小
                .initialCapacity(100)
                // 缓存的最大条数
                .maximumSize(config.MAX_CACHE_NUM)
                .scheduler(Scheduler.forScheduledExecutorService(Executors.newScheduledThreadPool(1)))
                .removalListener(new RemovalListener<String, Object>() {
                        @Override
                        public void onRemoval(@Nullable String key, @Nullable Object value, @NonNull RemovalCause cause) {
                            logger.debug("Cache Remove: key[" + key + "], value[" + value + "]");
                        }
                })
                // 设置最后一次写入或访问后经过固定时间过期
                .expireAfterWrite(config.MAX_DURATION, TimeUnit.SECONDS)
                .build();
    }
}
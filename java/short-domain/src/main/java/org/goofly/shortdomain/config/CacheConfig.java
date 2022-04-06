package org.goofly.shortdomain.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author : goofly
 * @Email: 709233178@qq.com
 */
@Service
@Slf4j
public class CacheConfig {

    private Cache<String, String> cache;

    @PostConstruct
    public void init() {
        cache = CacheBuilder.newBuilder()
                //并发级别
                .concurrencyLevel(8)
                //初始容量
                .initialCapacity(16)
                //最大容量为 Integer.MAX_VALU
                .maximumSize(Integer.MAX_VALUE)
                //过期时间
                .expireAfterWrite(2, TimeUnit.MINUTES)
                //移除监听
                .removalListener(notification -> log.info("cache invalid. key:{}", notification.getKey()))
                .build();
    }

    public String get(String key) {
        if (Objects.isNull(key)) {
            return null;
        }
        return cache.getIfPresent(key);
    }

    public void put(String key, String value) {
        if (Objects.nonNull(key)) {
            cache.put(key, value);
        }
    }
}

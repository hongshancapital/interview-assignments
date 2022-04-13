package test.utils;

import com.google.common.base.Ticker;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LocalCacheBuilder {

    public static <K, V> Cache<K, Optional<V>> build(int concurrencyLevel, long duration, TimeUnit timeUnit,
        int initialCapacity, long maximumSize) {
        final Cache<K, Optional<V>> cache =
            CacheBuilder.newBuilder()
                //设置并发级别为4，并发级别是指可以同时写缓存的线程数
                .concurrencyLevel(concurrencyLevel)
                //设置写缓存后5分钟过期
                .expireAfterWrite(duration, timeUnit)
                //定义缓存对象失效的时间精度为纳秒级
                .ticker(Ticker.systemTicker())
                .softValues()
                //设置缓存容器的初始容量
                .initialCapacity(initialCapacity)
                //设置缓存最大容量，超过之后就会按照LRU最近虽少使用算法来移除缓存项
                .maximumSize(maximumSize)
                //设置要统计缓存的命中率
                .recordStats()
                .removalListener(notification -> log
                    .debug(notification.getKey() + " 该key已被移除,原因:" + notification.getCause()))
                .build();
        return cache;
    }

}

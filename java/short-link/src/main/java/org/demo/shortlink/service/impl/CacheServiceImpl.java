package org.demo.shortlink.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.Weigher;
import org.demo.shortlink.service.CacheService;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author wsq
 * @date 2022/3/26 16:16
 * @description:
 */
@Service
public class CacheServiceImpl implements CacheService {

    private static final Cache<String, String> CACHE = CacheBuilder.newBuilder()
            // 能同时写存储的线程数量, 由于应用是 IO 密集型, 应该设置为 2*CPU 核心数
            .concurrencyLevel(Runtime.getRuntime().availableProcessors() * 2)
            .initialCapacity(10)
            // 设置最大能使用的存储空间字节数, 整个堆 2G, 老年代为 1.6G, 老年代0.4G
            // 剩下的空间给 guava 中的数组, entry 等使用
            .maximumWeight(100_000_000L)
            //设置写缓存后n秒钟过期
            .expireAfterWrite(3600, TimeUnit.SECONDS)
            // 每对长短链接的映射所占用的内存的计算, 按照每一个字符占用一个 char 类型的长度来计算
            .weigher((Weigher<String, String>) (key, value) -> (key.length() + value.length()) * 2)
            // 记录存储状态, 可以观察存储使用情况
            //.recordStats()
            .build();

    @Override
    public void put(String key, String value) {
        CACHE.put(key, value);
    }

    @Override
    public String get(String key) {
        return CACHE.getIfPresent(key);
    }
}

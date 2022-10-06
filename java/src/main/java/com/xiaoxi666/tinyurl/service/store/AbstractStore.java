package com.xiaoxi666.tinyurl.service.store;

import com.github.benmanes.caffeine.cache.LoadingCache;

/**
 * @Author: xiaoxi666
 * @Date: 2022/4/17
 * @Version: 1.0
 * @Description: 缓存存储
 */

public abstract class AbstractStore implements Store {
    // 容量设置估计：
    // 若设置为100万：短链接8位占用56字节，约消耗50MB内存空间。长连接无法预测，按照100字节计算的话，约消耗100MB空间。
    // 为了进行压测模拟出缓存逐出的场景，我们设置为10万。
    protected static final int MAX_SIZE = 1000_000;

    // 存储缓存。key：tinyPath，value：longUrl
    protected LoadingCache<String, String> cache;

    @Override
    public void put(String tinyPath, String longUrl) {
        cache.put(tinyPath, longUrl);
    }

    @Override
    public String get(String tinyurl) {
        return cache.get(tinyurl);
    }
}

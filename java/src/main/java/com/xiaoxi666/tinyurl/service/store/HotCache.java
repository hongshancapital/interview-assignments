package com.xiaoxi666.tinyurl.service.store;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author: xiaoxi666
 * @Date: 2022/4/17
 * @Version: 1.0
 * @Description: 热点缓存
 */
@Component
public class HotCache implements InitializingBean {
    // 热点缓存：防止同一个长链接不断生成短链接，消耗短链接号码。key：longUrl，value：tinyPath
    private LoadingCache<String, String> cache;

    // 最多存储1万条热点数据
    private static final int MAX_SIZE = 10_000;
    // 最多存储24小时热点数据
    private static final int MAX_HOUR = 24;


    @Override
    public void afterPropertiesSet() {
        cache = Caffeine.newBuilder()
                .maximumSize(MAX_SIZE)
                .expireAfterWrite(MAX_HOUR, TimeUnit.HOURS)
                .build(s -> "");
    }

    /**
     * 缓存长链接和短链接的映射关系
     *
     * @param longUrl
     * @param tinyPath
     */
    public void put(String longUrl, String tinyPath) {
        cache.put(longUrl, tinyPath);
    }

    /**
     * 尝试获取长链接对应的短链接，防止短时间内同一个长链接不断生成不同的短链接
     *
     * @param longUrl
     * @return
     */
    public String get(String longUrl) {
        return cache.get(longUrl);
    }

    /**
     * 清理key为longUrl的缓存
     * @param longUrl
     */
    public void clear(String longUrl) {
        cache.invalidate(longUrl);
    }
}

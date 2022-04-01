package com.zz.store;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.zz.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

/**
 * 短链接存储，存储主要的映射关系，实现lru算法，容量不够时，排除掉旧的数据
 *
 * @author zz
 * @version 1.0
 * @date 2022/3/31
 */
@Slf4j
@Repository
public class ShortUrlStore {

    @Value("${shortCode.maxSize}")
    private int maxSize;
    /**
     * 保存内存缓存
     */
    private Cache<String, String> localCache;

    @PostConstruct
    public void init() {
        //使用soft reference
        localCache = CacheBuilder.newBuilder().softValues().maximumSize(maxSize).concurrencyLevel(Constants.CPU_CORE).build();
    }

    /**
     * 存储映射关系
     *
     * @param url
     * @param shortCode
     */
    public void storeMapping(String url, String shortCode) {
        localCache.put(url, shortCode);
        localCache.put(shortCode, url);
    }

    /**
     * 根据url获取短码
     *
     * @param shortCode
     * @return
     */
    public String getUrlByShortCode(String shortCode) {
        return localCache.getIfPresent(shortCode);
    }

    /**
     * 根据短码获取url
     *
     * @param url
     * @return
     */
    public String getShortCodeByUrl(String url) {
        return localCache.getIfPresent(url);
    }
}

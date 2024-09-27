package com.liupf.tiny.url.repository.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.liupf.tiny.url.domain.TinyURL;
import com.liupf.tiny.url.repository.ITinyURLRepository;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.core.date.DateUnit;


/**
 * 工具类，确保缓存不会出现多份
 */
@Repository
public class TinyURLRepositoryImpl implements ITinyURLRepository {

    @Value("${cache.tiny-mapping.size}")
    private Integer mappingSize;

    @Value("${cache.long-mapping.minute}")
    private Integer mappingMinute;

    /**
     * Map<短Code, TinyURL>
     */
    private Cache<String, TinyURL> tinyUrlCache;

    /**
     * Weak Map<长URL, TinyURL>
     */
    private Cache<String, TinyURL> longUrlCache;

    @PostConstruct
    public void init() {
        tinyUrlCache = CacheUtil.newFIFOCache(mappingSize);
        longUrlCache = CacheUtil.newWeakCache(DateUnit.MINUTE.getMillis() * mappingMinute);
    }

    /**
     * 保存长链接
     * tip：cache写操作自带Lock
     */
    public void saveTinyUrl(TinyURL tinyURL) {
        longUrlCache.put(tinyURL.getLongUrl(), tinyURL);
        tinyUrlCache.put(tinyURL.getCode(), tinyURL);
    }

    /**
     * 通过Code查询TinyURL
     */
    public TinyURL findByCode(String code) {
        return tinyUrlCache.get(code);
    }


    /**
     * 通过长链接查询TinyURL
     */
    public TinyURL findByLongUrl(String longUrl) {
        return longUrlCache.get(longUrl);
    }

}

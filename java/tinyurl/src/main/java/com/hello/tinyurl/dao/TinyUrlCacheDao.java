package com.hello.tinyurl.dao;

import com.hello.tinyurl.util.ConvertUtil;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @author: Shuai
 * @date: 2021-7-19 14:36
 * @description:
 */
@Repository("tinyUrlCacheDao")
public class TinyUrlCacheDao {

    /**
     * cache key: id,
     * value: originalUrl
     * cache key: originalUrl,
     * value: id
     */
    String CACHE_KEY = "CACHE_KEY";

    @Resource
    private CacheManager cacheManager;

    @Resource
    private UniqueIdDao uniqueIdDao;

    public String saveOriginalUrl(String originalUrl) throws Exception {
        if (!StringUtils.hasText(originalUrl)) {
            throw new Exception("input originalUrl empty.");
        }
        try {
            Cache cache = cacheManager.getCache(CACHE_KEY);
            Assert.notNull(cache, "Cache must not be null");
            Cache.ValueWrapper value = cache.get(originalUrl);
            String tinyUrl;
            if (value != null) {
                tinyUrl = (String) value.get();
            } else {
                Long id = uniqueIdDao.getUniqueId();
                tinyUrl = ConvertUtil.num2String(id);
                cache.putIfAbsent(tinyUrl, originalUrl);
                cache.putIfAbsent(originalUrl, tinyUrl);
            }
            return tinyUrl;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    public String getOriginalUrl(String tinyUrl) throws Exception {
        if (tinyUrl == null){
            throw new Exception("input tinyUrl empty.");
        }
        try {
            Cache cache = cacheManager.getCache(CACHE_KEY);
            Assert.notNull(cache, "Cache must not be null");
            String originalUrl = cache.get(tinyUrl, String.class);
            if (StringUtils.hasText(originalUrl)) {
                return originalUrl;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}

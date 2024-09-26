package com.hszb.shorturl.manager.storage;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;

/**
 * @Author: xxx
 * @License: (C) Copyright 2005-2019, xxx Corporation Limited.
 * @Date: 2021/12/19 11:33 上午
 * @Version: 1.0
 * @Description:
 */
public class ShortUrlStorage {

    private ShortUrlStorage () {
        init();
    }

    // 长域名存储 key:短域名code  value: 长域名地址
    private  Cache<String, String> longUrlCache;

    // 短域名存储 key:长域名hashcode  value:短域名code
    private  Cache<Integer, String> shortUrlCache;

    private static class ShortUrlHolder {
        private static final ShortUrlStorage instance = new ShortUrlStorage();
    }

    public static ShortUrlStorage getInstance() {
        return ShortUrlHolder.instance;
    }

    private void init () {
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true);
        CacheConfiguration<String, String> longCconfiguration  =
                CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, String.class,
                        ResourcePoolsBuilder.newResourcePoolsBuilder().offheap(1, MemoryUnit.GB)).build();
        ResourcePoolsBuilder.newResourcePoolsBuilder().heap(10000, EntryUnit.ENTRIES);

        CacheConfiguration<Integer, String> shortCconfiguration  =
                CacheConfigurationBuilder.newCacheConfigurationBuilder(Integer.class, String.class,
                        ResourcePoolsBuilder.newResourcePoolsBuilder().offheap(512, MemoryUnit.MB)).build();
        ResourcePoolsBuilder.newResourcePoolsBuilder().heap(10000, EntryUnit.ENTRIES);

        longUrlCache = cacheManager.createCache("longUrlCache", longCconfiguration);
        shortUrlCache = cacheManager.createCache("shortUrlCache", shortCconfiguration);
    }

    public void saveShortUrl (String shortCode, String longUrl) {
        longUrlCache.put(shortCode, longUrl);
        shortUrlCache.put(longUrl.hashCode(), shortCode);
    }

    public String queryLongUrl (String shortCode) {
        if (null != shortCode) {
            return longUrlCache.get(shortCode);
        }
        return null;
    }

    public String queryShortUrl (String longUrl) {
        if (null != longUrl) {
            return shortUrlCache.get(longUrl.hashCode());
        }
        return null;
    }
}

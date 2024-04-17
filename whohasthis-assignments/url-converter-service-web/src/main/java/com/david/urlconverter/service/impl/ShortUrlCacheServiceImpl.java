package com.david.urlconverter.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.david.urlconverter.service.web.IShortUrlLocalCacheService;

@Service
public class ShortUrlCacheServiceImpl implements IShortUrlLocalCacheService {

    @Autowired
    private Cache<String, String> caffeineCache;

    @Override
    public void storeShortToLongMapping(String shortUrl, String longUrl) {
        caffeineCache.put(shortUrl,longUrl);
    }

    @Override
    public String queryLongUrlInCache(String shortUrl) {
        return caffeineCache.getIfPresent(shortUrl);
    }
}

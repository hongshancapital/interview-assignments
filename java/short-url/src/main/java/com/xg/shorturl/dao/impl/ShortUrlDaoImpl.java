package com.xg.shorturl.dao.impl;

import com.xg.shorturl.cache.ThreadSafeLRUCache;
import com.xg.shorturl.dao.ShortUrlDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

/**
 * @author xionggen
 */
@Repository
public class ShortUrlDaoImpl implements ShortUrlDao {

    @Value("${LRUCache.maxSize}")
    private Integer maxSize;

    private ThreadSafeLRUCache lruCache;

    @PostConstruct
    public void init() {
        lruCache = new ThreadSafeLRUCache(maxSize);
    }

    @Override
    public String queryOriginalUrl(String shortUrl) {
        return lruCache.getOriginalUrl(shortUrl);

    }

    @Override
    public String queryShortUrl(String originalUrl) {
        return lruCache.getShortUrl(originalUrl);
    }

    @Override
    public int save(String shortUrl, String originalUrl) {
        return lruCache.save(shortUrl, originalUrl);
    }
}

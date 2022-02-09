package com.scdt.china.shorturl.service.internal;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.scdt.china.shorturl.service.ShortUrlRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CaffeineShortUrlRepository implements ShortUrlRepository, InitializingBean {

    @Value("${caffeine.initialCapacity:5}")
    private int initialCapacity = 5;

    @Value("${caffeine.maximumSize:100000}")
    private int maximumSize;

    private Cache<String /** shortUrl */, String /** url*/> short2Url;

    private Cache<String /** url */, String /** shortUrl */> url2Short;

    /**
     * 存储短链的线程安全Set，用来做并发控制
     */
    private Set<String> shortUrlSet = new ConcurrentHashMap<String,String>().newKeySet();

    @Override
    public void afterPropertiesSet() throws Exception {
        short2Url = Caffeine.newBuilder()
                .initialCapacity(initialCapacity)
                .maximumSize(maximumSize)
                .removalListener((key,value,cause) -> {
                    /**
                     * 数据同步
                     */
                    url2Short.invalidate(value);
                    shortUrlSet.remove(key);
                })
                .build();

        url2Short = Caffeine.newBuilder()
                .initialCapacity(initialCapacity)
                .maximumSize(maximumSize)
                .build();
    }

    @Override
    public boolean addMapping(String shortUrl, String url) {

        if (shortUrlSet.contains(shortUrl)) {
            return false;
        }

        if (shortUrlSet.add(shortUrl)) {
            short2Url.put(shortUrl, url);
            url2Short.put(url,shortUrl);
            return true;
        }

        return false;
    }

    @Override
    public String fetchShortUrl(String url) {
        return url2Short.getIfPresent(url);
    }

    @Override
    public String fetchUrl(String shortUrl) {
        return short2Url.getIfPresent(shortUrl);
    }
}

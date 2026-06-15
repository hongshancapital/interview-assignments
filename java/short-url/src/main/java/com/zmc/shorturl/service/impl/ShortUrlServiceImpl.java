package com.zmc.shorturl.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.zmc.shorturl.service.ShortUrlService;
import com.zmc.shorturl.vo.UrlVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Description: 短链接接口实现类
 * Author: Zmc
 * Date: 2021-12-11 18:20
 **/
@Service
public class ShortUrlServiceImpl implements ShortUrlService, InitializingBean {


    @Value("${short.url.cached.max:5000000}")
    private int maxShortUrlCached;

    @Value("${default.domain}")
    public  String DEFAULT_DOMAIN;

    private final ConcurrentMap<String, UrlVo> shortUrlMap = new ConcurrentHashMap<>();

    private Cache<String, UrlVo> cached;

    private AtomicLong incr = new AtomicLong(0);

    /**
     * 混淆排序
     */
    private static final String URL_BASE = "LBClqO43AIbTMDy8G1eVZmF0hutRkzXdQ6wNfiKEJaxWHPSg57rj9nosY2vUcp";


    @Override
    public String shortUrl(String longUrl) throws ExecutionException {
        UrlVo shortUrl;
        shortUrl = cached.get(longUrl, () -> {
            long id = incr.incrementAndGet();
            String urlKey = toBase62(id);
            UrlVo urlValue = new UrlVo(id, urlKey, longUrl);
            shortUrlMap.put(urlKey, urlValue);
            return urlValue;
        });
        return DEFAULT_DOMAIN +shortUrl.getShortUrl();
    }

    @Override
    public String parseLongUrl(String shortUrl) {
        shortUrl = shortUrl.replace(DEFAULT_DOMAIN, "");
        UrlVo urlVo = shortUrlMap.get(shortUrl);
        if (Objects.isNull(urlVo)) {
            return null;
        }
        return urlVo.getLongUrl();
    }

    @Override
    public void afterPropertiesSet() {
        cached = CacheBuilder.newBuilder()
                .maximumSize(maxShortUrlCached)
                .removalListener((RemovalListener<String, UrlVo>) notification -> {
                    UrlVo urlVo = notification.getValue();
                    if (Objects.nonNull(urlVo) ) {
                        shortUrlMap.remove(urlVo.getShortUrl());
                    }
                })
                .expireAfterWrite(Long.MAX_VALUE, TimeUnit.DAYS).build();
    }


    public static String toBase62(long num) {
        StringBuilder sb = new StringBuilder();
        do {
            int i = (int) (num % 62);
            sb.append(URL_BASE.charAt(i));
            num /= 62;
        } while (num > 0);

        return sb.reverse().toString();
    }
}

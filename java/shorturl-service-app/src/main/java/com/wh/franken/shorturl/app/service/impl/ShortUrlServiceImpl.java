package com.wh.franken.shorturl.app.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.wh.franken.shorturl.app.service.IdGeneratorService;
import com.wh.franken.shorturl.app.service.ShortUrlService;
import com.wh.franken.shorturl.app.vo.UrlVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 采用生成自增ID，转换成62进制生成短域名的方式处理
 * 只要ID不超过 281474976710656 62的8次方，就可以保证位数在8位
 *
 * @author fanliang
 */
@RequiredArgsConstructor
@Service
public class ShortUrlServiceImpl implements ShortUrlService, InitializingBean {

    private final IdGeneratorService idGeneratorService;

    @Value("${short.url.cached.max:5000000}")
    private int maxShortUrlCached;

    private final ConcurrentMap<String, UrlVo> shortUrlMap = new ConcurrentHashMap<>();

    private Cache<String, UrlVo> cached;

    private static final String BASE = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @Override
    public String generatorShortUrl(String longUrl) throws ExecutionException {
        UrlVo shortUrl;
        shortUrl = cached.get(longUrl, () -> {
            long id = idGeneratorService.incrId();
            String urlKey = toBase62(id);
            UrlVo urlValue = new UrlVo(id, urlKey, longUrl);
            shortUrlMap.put(urlKey, urlValue);
            return urlValue;
        });
        return shortUrl.getShortUrl();
    }

    @Override
    public String parseLongUrl(String shortUrl) {
        UrlVo urlVo = shortUrlMap.get(shortUrl);
        if (null == urlVo) {
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
                    if (null != urlVo) {
                        shortUrlMap.remove(urlVo.getShortUrl());
                    }
                })
                .expireAfterWrite(Long.MAX_VALUE, TimeUnit.DAYS).build();
    }


    private static String toBase62(long num) {
        StringBuilder sb = new StringBuilder();
        do {
            int i = (int) (num % 62);
            sb.append(BASE.charAt(i));
            num /= 62;
        } while (num > 0);

        return sb.reverse().toString();
    }
}

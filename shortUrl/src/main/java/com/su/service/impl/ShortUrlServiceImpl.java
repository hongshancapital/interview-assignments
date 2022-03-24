package com.su.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.su.service.IShortUrlService;
import com.su.service.IUrlSuffixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author hejian
 * 短域名服务-实现
 */
@Service
public class ShortUrlServiceImpl implements IShortUrlService {

    private String prefix;

    private IUrlSuffixService urlSuffixService;

    private Cache<String, String> shortUrlCache =
            CacheBuilder.newBuilder().expireAfterWrite(15, TimeUnit.MINUTES).maximumSize(1000).build();

    private Cache<String, String> longUrlCache =
            CacheBuilder.newBuilder().expireAfterWrite(15, TimeUnit.MINUTES).maximumSize(1000).build();


    public ShortUrlServiceImpl(@Value("${su.prefix}") String prefix, @Autowired IUrlSuffixService urlSuffixService) {
        this.prefix = prefix;
        this.urlSuffixService = urlSuffixService;
    }

    @Override
    public String getShortUrl(String url) {
        if (longUrlCache.asMap().containsKey(url)) {
            return longUrlCache.asMap().get(url);
        }
        String surl = url;
        boolean flag = false;
        while (true) {
            String shortUrl = prefix + urlSuffixService.getSuffix(surl);
            if (!shortUrlCache.asMap().keySet().contains(shortUrl)) {
                shortUrlCache.put(shortUrl, url);
                longUrlCache.put(url, shortUrl);
                return shortUrl;
            }
            surl = url + "/" + System.currentTimeMillis();
        }
    }

    @Override
    public String getLongUrl(String shortUrl) {
        if (shortUrlCache.asMap().containsKey(shortUrl)) {
            return shortUrlCache.asMap().get(shortUrl);
        }
        throw new RuntimeException(shortUrl + " 非法");
    }
}

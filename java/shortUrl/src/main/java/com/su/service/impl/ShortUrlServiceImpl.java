package com.su.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.su.service.IShortUrlService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @author hejian
 * 短域名服务-实现
 */
@Service
public class ShortUrlServiceImpl implements IShortUrlService {

    @Value("${su.prefix}")
    private String prefix;

    private Cache<String, String> cache =
            CacheBuilder.newBuilder().expireAfterWrite(15, TimeUnit.MINUTES).build();

    @Override
    public String getShortUrl(String url) {
        HashCode hashCode = Hashing.murmur3_32().hashString(url, StandardCharsets.UTF_8);
        String shortUrl = prefix + hashCode.toString();
        cache.put(shortUrl, url);
        return shortUrl;
    }

    @Override
    public String getLongUrl(String shortUrl) {
        if (cache.asMap().containsKey(shortUrl)) {
            return cache.asMap().get(shortUrl);
        }
        throw new RuntimeException(shortUrl + " 非法");
    }
}

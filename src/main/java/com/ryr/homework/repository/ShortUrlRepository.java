package com.ryr.homework.repository;

import com.google.common.cache.LoadingCache;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.concurrent.ExecutionException;

@Component
public class ShortUrlRepository {

    @Autowired
    public LoadingCache<String, String> shortUrlCache;

    //布隆过滤器记录已存在的短域名  长度1000w
    private final BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()), 10000000, 0.0001);

    //保存长域名 返回id
    public void saveLongUrl(String longUrl, String shortUrl) {
        shortUrlCache.put(shortUrl, longUrl);
        bloomFilter.put(shortUrl);
    }

    public boolean existShortUrl(String shortUrl) {
        return bloomFilter.mightContain(shortUrl);
    }

    //通过短域名获取长域名
    public String getLongUrl(String shortUrl) {
        try {
            return shortUrlCache.get(shortUrl);
        } catch (ExecutionException e) {
            return "获取长域名失败";
        }
    }
}

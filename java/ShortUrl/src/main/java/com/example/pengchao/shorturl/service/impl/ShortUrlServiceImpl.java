package com.example.pengchao.shorturl.service.impl;

import com.example.pengchao.shorturl.service.ShortUrlService;
import com.example.pengchao.shorturl.utils.ShortUrlUtils;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 短url的服务实现类
 *
 * @author pengchao04
 * @date 2022/5/23 11:01
 */
@Service
@Slf4j
public class ShortUrlServiceImpl implements ShortUrlService {

    /**
     * 短url对应的长url
     */
    public static Map<String,String> shortUrlCacheMap = new ConcurrentHashMap<>();
    /**
     * 长url 对应的 短url
     */
    public static Map<String,String> longUrlCacheMap = new ConcurrentHashMap<>();

    @Override
    public String getLongUrl(String shortUrl) {
        if (shortUrlCacheMap.containsKey(shortUrl)){
            return shortUrlCacheMap.get(shortUrl);
        }
        throw new RuntimeException("未找到短url对应的长url");
    }

    @Override
    public String getShortUrl(String longUrl) {
        if (longUrlCacheMap.containsKey(longUrl)) {
            return longUrlCacheMap.get(longUrl);
        }

        String shortUrl = ShortUrlUtils.generateShortenUrl(longUrl);
        log.info("long url: {}, short url: {}", longUrl, shortUrl);
        longUrlCacheMap.put(longUrl, shortUrl);
        shortUrlCacheMap.put(shortUrl, longUrl);
        return shortUrl;
    }
}

package com.david.urlconverter.service.impl;

import com.david.urlconverter.service.dubbo.IUrlConverterStorageSOAService;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author whohasthis
 */
//juint test use
//@Service
@DubboService(version = "${urlConverter.service.version}")
@Slf4j
public class UrlConverterStorageSOAServiceImpl implements IUrlConverterStorageSOAService {

    @Autowired
    private Cache<String, String> shortToLongCaffeineCache;

    @Autowired
    private Cache<String, String> longToShortCaffeineCache;


    /**
     * 根据短域名，查询长域名
     * @param shortUrl
     * @return
     */
    @Override
    public String retrieveLongUrl(String shortUrl) {
        return shortToLongCaffeineCache.getIfPresent(shortUrl);
    }

    /**
     * 根据长域名，查询短域名
     * @param longUrl
     * @return
     */
    @Override
    public String queryShortUrl(String longUrl) {
        return longToShortCaffeineCache.getIfPresent(longUrl);
    }

    /**
     * 保存长短域名的映射
     * @param shortUrl
     * @param longUrl
     */
    @Override
    public void storeUrlMapping(String shortUrl, String longUrl) {
        shortToLongCaffeineCache.put(shortUrl, longUrl);
        longToShortCaffeineCache.put(longUrl, shortUrl);
    }
}

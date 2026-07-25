package com.example.demo.service.impl;

import com.example.demo.dao.CacheDB;
import com.example.demo.service.ShortUrlService;
import com.example.demo.utils.MurmurHashShortUrlGenerator;
import com.google.common.base.Strings;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName ShortURLServiceImpl
 * @Description TODO
 * @Author gongguanghui
 * @Date 2021/11/25 4:30 PM
 * @Version 1.0
 **/
@Service
public class ShortUrlServiceImpl implements ShortUrlService {
    //可改为动态配置，根据不同业务短链接地址不同
//    private final String shortUrlPrefix = "http://a.cn/";


    @Resource
    private CacheDB cacheDB;

    @Override
    public String storeShortUrl(String longUrl) {
        String shortPath = MurmurHashShortUrlGenerator.shortenUrl(longUrl);
//        String shortUrl = shortUrlPrefix + shortPath;
        cacheDB.setCache(shortPath, longUrl);
        return shortPath;
    }

    @Override
    public String getLongUrl(String shortUrl) {
        String longUrl = cacheDB.getLongURLFromCache(shortUrl);
        return longUrl;
    }
}

package com.service.impl;

import com.config.GetshortUrl;
import com.config.ResultException;
import com.config.ResultStatus;
import com.service.DomainNameService;
import com.service.MatchStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author jeffrey
 * @Date 2021/10/11
 * @description:
 */
@Service
@Slf4j
public class DomainNameServiceImpl implements DomainNameService {

    public static final int MAX_CACHE_SIZE = 10000;
    public static final String BASE_URL = "https://o.cn";
    @Autowired
    private MatchStorageService matchStorageService;

    @Override
    public String getShortUrl(String longUrl) {
        log.info("原始长链接地址{}",longUrl);
        Optional<String> cacheShortUrl = matchStorageService.getShortUrlByLongUrl(longUrl);
        if (cacheShortUrl.isPresent()) {
            return generateFullShortUrlByShortKey(cacheShortUrl.get());
        } else {
            long storeSize = matchStorageService.getCacheSize();
            if (storeSize >= MAX_CACHE_SIZE) {
                log.warn("max cache size reached, " + storeSize);
                throw new ResultException(ResultStatus.SERVICE_UNAVAILABLE, "service temporary unavailable");
            }
            String shortKey = generateAndCacheUrl(longUrl);
            return generateFullShortUrlByShortKey(shortKey);
        }
    }

    @Override
    public String getLongUrl(String shortUrl) {
        log.info("get long url:{} ",shortUrl);
        String[] splitUrls = shortUrl.split("/");
        String shortKey = splitUrls[3];
        Optional<String> cacheLongUrl = matchStorageService.getLongUrlByShortUrl(shortKey);
        if (cacheLongUrl.isPresent()) {
            return cacheLongUrl.get();
        } else {
            log.info("get long url failed, shortUrl:{} ", shortUrl);
            throw new ResultException(ResultStatus.LONG_URL_NOT_FOUND, "long url not found");
        }
    }

    private String generateAndCacheUrl(String longUrl) {
        String[] shortKeys = GetshortUrl.shortUrl(longUrl);
        for(String shortKey:shortKeys){
            Optional<String> cacheLongUrl = matchStorageService.getShortUrlByLongUrl(shortKey);
            if(!cacheLongUrl.isPresent()){
                matchStorageService.setUrlMatch(longUrl, shortKey);
                return shortKey;
            }
        }
        throw new ResultException(ResultStatus.GENERATE_REPEAT, "generate shortkey repeat");
    }

    private String generateFullShortUrlByShortKey(String shortKey) {
        return BASE_URL + "/" + shortKey;
    }
}

package com.service.impl;

import com.constant.CommonConstants;
import com.exception.SystemException;
import com.service.IDomainNameService;
import com.service.IMatchStorageService;
import com.utils.ShortUrlUtils;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DomainNameServiceImpl implements IDomainNameService {

    private Logger logger = LoggerFactory.getLogger(DomainNameServiceImpl.class);

    @Autowired
    private IMatchStorageService matchStorageService;

    @Override
    public String getShortUrl(String longUrl) {
        logger.info("get short url: " + longUrl);
        Optional<String> cacheShortUrl = matchStorageService.getShortUrlByLongUrl(longUrl);
        if (cacheShortUrl.isPresent()) {
            return generateFullShortUrlByShortKey(cacheShortUrl.get());
        } else {
            long storeSize = matchStorageService.getCacheSize();
            if (storeSize >= CommonConstants.MAX_CACHE_SIZE) {
                logger.warn("max cache size reached, " + storeSize);
                throw new SystemException(CommonConstants.CODE_SERVICE_UNAVAILABLE, "service temporary unavailable");
            }
            String shortUrl = generateAndCacheUrl(longUrl);
            return generateFullShortUrlByShortKey(shortUrl);
        }
    }

    @Override
    public String getLongUrl(String shortUrl) {
        logger.info("get long url: " + shortUrl);
        String[] splitUrls = shortUrl.split("/");
        String shortKey = splitUrls[3];
        Optional<String> cacheShortUrl = matchStorageService.getLongUrlByShortUrl(shortKey);
        if (cacheShortUrl.isPresent()) {
            return generateFullShortUrlByShortKey(cacheShortUrl.get());
        } else {
            throw new SystemException(CommonConstants.CODE_LONG_URL_NOT_FOUND, "long url not found");
        }
    }

    private String generateAndCacheUrl(String longUrl) {
        Integer key = 10000;
        String[] shortUrls = ShortUrlUtils.getShortText(longUrl, key.toString());
        for (int j = 0; j < shortUrls.length; j += 1) {
            String shortUrl = shortUrls[j];
            Optional<String> value = matchStorageService.getLongUrlByShortUrl(shortUrl);
            if (!value.isPresent()) {
                logger.info("set url match, longUrl: " + longUrl + ", shortUrl: " + shortUrl);
                matchStorageService.setUrlMatch(longUrl, shortUrl);
                return shortUrl;
            }
        }
        logger.info("all the candidate shortKey is conflict, source longUrl is: " + longUrl);
        throw new SystemException(CommonConstants.CODE_SERVICE_UNAVAILABLE, "service temporary unavailable");
    }

    private String generateFullShortUrlByShortKey(String shortKey) {
        return CommonConstants.BASE_URL + "/" + shortKey;
    }
}

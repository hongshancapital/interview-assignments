package com.hongshan.interfacejob.service.impl;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import com.hongshan.interfacejob.constant.CommonConstants;
import com.hongshan.interfacejob.exception.SystemException;
import com.hongshan.interfacejob.service.DomainNameService;
import com.hongshan.interfacejob.service.MatchStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DomainNameServiceImpl implements DomainNameService {

    private Logger logger = LoggerFactory.getLogger(DomainNameServiceImpl.class);

    @Autowired
    private MatchStorageService matchStorageService;

    private AtomicInteger urlCounter = new AtomicInteger(0);

    @Override
    public String getShortUrl(String longUrl) {
        logger.info("get short url: " + longUrl);
        String result = "";

        Optional<String> cacheShortUrl = matchStorageService.getShortUrlByLongUrl(longUrl);
        if (cacheShortUrl!=null && cacheShortUrl.isPresent()) {
            result = cacheShortUrl.get();
        } else {
            logger.info("get short url failed, shortUrl: " + longUrl);
            throw new SystemException(CommonConstants.CODE_SHORT_URL_NOT_FOUND, "没有找到对应的短域名");
        }
        return result;
    }

    @Override
    public String getLongUrl(String shortUrl) {
        logger.info("get long url: " + shortUrl);
        String result = "";

        String[] splitUrls = shortUrl.split("/");
        String shortKey = null;
        if (shortUrl.startsWith("http")) {
            shortKey = splitUrls[3];
        }else{
            shortKey = shortUrl;
        }

        Optional<String> cacheLongUrl = matchStorageService.getLongUrlByShortUrl(shortKey);
        if (cacheLongUrl.isPresent()) {
            result = cacheLongUrl.get();
        } else {
            logger.info("get long url failed, shortUrl: " + shortUrl);
            throw new SystemException(CommonConstants.CODE_LONG_URL_NOT_FOUND, "没有找到对应的长域名");
        }
        return result;
    }

}
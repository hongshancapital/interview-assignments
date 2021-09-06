package com.sequcap.shorturl.service.impl;

import com.sequcap.shorturl.service.UrlManagementService;
import com.sequcap.shorturl.web.model.UrlModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UrlManagementServiceImpl implements UrlManagementService {

    Logger log = LoggerFactory.getLogger(UrlManagementServiceImpl.class);

    private static final String SHORT_DOMAIN = "http://sequcap.cn/";

    @Override
    @Cacheable(value="UrlConvert:UrlModel", key="#murmurCode")
    public UrlModel getUrlModel(String murmurCode) {
        log.info("****The UrlModel is not exist or expired. short url = {}", SHORT_DOMAIN +murmurCode);
        return null;
    }

    @Override
    @CachePut(value="UrlConvert:UrlModel", key="#murmurCode")
    public UrlModel buildUrlModel(String murmurCode, String longUrl) {
        log.info("**** Generator UrlModel by longUrl and murmurCode, longUrl={}, murmurCode={}", longUrl, murmurCode);
        UrlModel shortUrl = new UrlModel();
        shortUrl.setLongUrl(longUrl);
        shortUrl.setShortUrl(SHORT_DOMAIN +murmurCode);
        shortUrl.setMurmurCode(murmurCode);
        shortUrl.setCreatedDate(new Date());

        return shortUrl;
    }

}

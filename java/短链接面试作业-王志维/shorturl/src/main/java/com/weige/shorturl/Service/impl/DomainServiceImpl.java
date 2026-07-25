package com.weige.shorturl.Service.impl;

import com.weige.shorturl.Service.DomainService;
import com.weige.shorturl.common.MemoryUtils;
import com.weige.shorturl.common.ShortUrlUtils;
import com.weige.shorturl.common.Storage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("domainService")
public class DomainServiceImpl implements DomainService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${short.url.prefix}")
    private String shortUrlPrefix;

    @Override
    public String write(String longUrl) {

        try {
            if (MemoryUtils.checkMemoryEnough()){
                String shortUrl = shortUrlPrefix+ ShortUrlUtils.shortUrl(longUrl);
                logger.info("save shortUrl:"+shortUrl+",longUrl:"+longUrl);
                Storage.set(shortUrl,longUrl);
                return shortUrl;
            }
        }catch (Exception e){
            logger.error("jvm内存不足，无法操作");
        }
        return "jvm内存不足，无法操作";
    }

    @Override
    public String read(String shortUrl) {
        if (StringUtils.isEmpty(shortUrl)){
            return null;
        }
        logger.info("read shortUrl:"+shortUrl+",longUrl:"+ Storage.find(shortUrl));
        return Storage.find(shortUrl);
    }
}

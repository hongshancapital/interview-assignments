package com.getao.urlconverter.service.impl;

import com.getao.urlconverter.dto.generator.NumGeneratorPool;
import com.getao.urlconverter.dto.vo.GetLongUrlVO;
import com.getao.urlconverter.dto.vo.GetShortUrlVO;
import com.getao.urlconverter.dto.vo.UrlCacheVO;
import com.getao.urlconverter.exception.UrlConvertException;
import com.getao.urlconverter.service.UrlConverterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
@Service
public class UrlConverterServiceImpl implements UrlConverterService {

    private NumGeneratorPool generatorPool;

    // private static final ReentrantReadWriteLock LOCK = new ReentrantReadWriteLock();

    @Autowired
    private UrlCacheServiceImpl urlCacheService;

    /**
     * 初始化发号器池
     */
    @PostConstruct
    private void init() {
        generatorPool = new NumGeneratorPool();
    }

    @Override
    public GetShortUrlVO getShortUrl(String longUrl) {
        if (StringUtils.isEmpty(longUrl)) {
            throw new UrlConvertException("Cannot convert empty URL.");
        }
        if(urlCacheService.exists(longUrl)) {
            UrlCacheVO urlCacheVO = urlCacheService.getCache(longUrl);
            return new GetShortUrlVO(200,
                    urlCacheVO.getResUrl(),
                    "Get existing short URL success."
            );
        } else {
            String shortUrl = generatorPool.getEncodedUrl();
            UrlCacheVO urlCacheVO = urlCacheService.putInCache(longUrl, shortUrl);
            return new GetShortUrlVO(200,
                    urlCacheVO.getResUrl(),
                    "New short URL generated."
            );
        }
    }

    @Override
    public GetLongUrlVO getLongUrl(String shortUrl) {
        if(StringUtils.isEmpty(shortUrl)) {
            throw new UrlConvertException("Empty URL is not allowed");
        }
        UrlCacheVO urlCacheVO = urlCacheService.getCache(shortUrl);
        if( urlCacheVO == null) {
            return new GetLongUrlVO(400,
                    null,
                    "This URL has never been recorded or expired. Please convert it to short URL first."
            );
        } else {
            return new GetLongUrlVO(200,
                    urlCacheVO.getResUrl(),
                    "Get existing long URL");
        }
    }
}
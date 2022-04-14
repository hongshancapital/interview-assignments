package com.scdt.china.shorturl.service.impl;

import com.scdt.china.shorturl.contants.CommonConstants;
import com.scdt.china.shorturl.service.ShortUrlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author：costa
 * @date：Created in 2022/4/12 11:12
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ShortUrlServiceImpl implements ShortUrlService {
    private final CacheManager cacheManager;

    public String setShortUrl(String url) {
        String shortCode = generateShortCode(url);

        Cache cache = cacheManager.getCache(CommonConstants.CODE2URL_MAP);
        if (cache != null) {
            cache.put(shortCode, url);
        }else{//缓存为空则返回NULL
            return null;
        }
        return shortCode;
    }

    private String generateShortCode(String url) {
        return "123";
    }

    @Cacheable(value=CommonConstants.CODE2URL_MAP,key="#shortCode")
    public String getUrl(String shortCode) {
        //正常不会进入，进入说明传进来的code已失效
        return null;
    }
}

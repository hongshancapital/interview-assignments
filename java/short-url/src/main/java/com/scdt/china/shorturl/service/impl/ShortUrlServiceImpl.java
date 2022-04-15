package com.scdt.china.shorturl.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.scdt.china.shorturl.constant.CommonConstants;
import com.scdt.china.shorturl.service.ShortUrlService;
import com.scdt.china.shorturl.util.ShortUrlGenerator;
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

    /**
     * 获取短域名
     */
    public String getShortUrl(String url) {
        String shortCode = ShortUrlGenerator.shortUrl(url);
        Cache cache = cacheManager.getCache(CommonConstants.CODE2URL_MAP);

        if (cache != null) {
            String cacheUrl = cache.get(shortCode, String.class);
            //1、没有数据正常放入
            //2、相等直接返回
            //3、不相等就把url md5一遍进行判断
            if (StrUtil.isEmpty(cacheUrl)) {
                cache.put(shortCode, url);
            } else if (url.equals(cacheUrl)) {
                return shortCode;
            } else {
                shortCode = ShortUrlGenerator.shortUrl(SecureUtil.md5(url));
                cacheUrl = cache.get(shortCode, String.class);
                //理论上不会有url.equals(cacheUrl)的情况，不考虑，不为空就返回null
                if (StrUtil.isEmpty(cacheUrl)) {
                    cache.put(shortCode, url);
                } else {//两次失败返回错误
                    log.error("shortCode:" + shortCode );
                    log.error("cacheUrl:" + cacheUrl );
                    return null;
                }
            }

        } else {//缓存为空则返回NULL
            log.error("cache is null" );
            return null;
        }
        return shortCode;
    }

    /**
     * 获取长域名（从缓存中直接拿）
     */
    @Cacheable(value = CommonConstants.CODE2URL_MAP, key = "#shortCode")
    public String getUrl(String shortCode) {
        //正常不会进入，进入说明传进来的code已失效或不存在,直接返回null
        return null;
    }
}

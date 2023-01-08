package com.eagle.shorturl.service.impl;

import com.eagle.shorturl.service.BloomFilterService;
import com.eagle.shorturl.service.CacheService;
import com.eagle.shorturl.service.ShortUrlService;
import com.eagle.shorturl.util.EncodeUtil;
import com.eagle.shorturl.util.HashUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * @author eagle
 * @description
 */
@Slf4j
@Service
@Validated
public class ShortUrlServiceImpl implements ShortUrlService {

    /**
     * 加盐系数
     */
    private static final String SALT = "DUPLICATION";

    @Resource
    private BloomFilterService bloomFilterService;

    @Resource
    private CacheService cacheService;

    @Override
    public String add(@NotBlank String longUrl) {
        StringBuilder tmpLongUrl = new StringBuilder(longUrl);
        String shortUrl = EncodeUtil.encode62(HashUtil.murmur32(tmpLongUrl.toString()));
        while (bloomFilterService.mightContain(shortUrl) && cacheService.contain(shortUrl)) {
            if (longUrl.equals(cacheService.get(shortUrl))) {
                return shortUrl;
            }
            tmpLongUrl.append(SALT);
            shortUrl = EncodeUtil.encode62(HashUtil.murmur32(tmpLongUrl.toString()));
        }
        bloomFilterService.put(shortUrl);
        cacheService.put(shortUrl, longUrl);
        return shortUrl;
    }

    @Override
    public String get(@NotBlank String shortUrl) {
        return cacheService.get(shortUrl);
    }
}

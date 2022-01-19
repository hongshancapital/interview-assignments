package com.example.shorturl.service.core;

import com.example.shorturl.config.GeneratorConfig;
import com.example.shorturl.config.exception.BizException;
import com.example.shorturl.service.dto.ShortUrlDto;
import com.example.shorturl.service.generator.GenerateFactory;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author yyp
 * @date 2022/1/16 16:47
 */
public abstract class CommonShortUrlService implements IShortUrlService {
    protected GenerateFactory factory;

    protected Cache<String, ShortUrlDto> shortUrlCache;

    protected GeneratorConfig config;

    CommonShortUrlService(GenerateFactory generateFactory, GeneratorConfig config) {
        this.factory = generateFactory;
        this.config = config;
        shortUrlCache = CacheBuilder.newBuilder()
                //设置2 * NCpu核数
                .concurrencyLevel(8)
                .initialCapacity(1024)
                .maximumSize(10000000)
                .expireAfterAccess(3600, TimeUnit.SECONDS)
                .expireAfterWrite(3600, TimeUnit.SECONDS)
                .build();
    }

    @Override
    public ShortUrlDto getShortUrl(String longUrl) {
        if (!StringUtils.hasLength(longUrl)) {
            throw new BizException("请输入需要转化的长链接");
        }
        String generateCode = factory.getGenerator().generateCode();
        ShortUrlDto shortUrlDto = new ShortUrlDto();
        shortUrlDto.setLongUrl(longUrl);
        shortUrlDto.setShortUrl(config.shortHost.concat(generateCode));
        shortUrlDto.setExpireTime(System.currentTimeMillis() + config.cacheSeconds);
        String shortUrl = shortUrlDto.getShortUrl();
        shortUrlCache.put(shortUrl, shortUrlDto);
        return shortUrlDto;
    }

    @Override
    public String getLongUrl(String shortUrl) {
        if (!StringUtils.hasLength(shortUrl)) {
            throw new BizException("请输入短链接");
        }
        return Optional.ofNullable(shortUrlCache.getIfPresent(shortUrl))
                .orElseThrow(()-> new BizException("获取长链接失败"))
                .getLongUrl();
    }
}

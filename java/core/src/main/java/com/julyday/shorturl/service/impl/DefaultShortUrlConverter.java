package com.julyday.shorturl.service.impl;

import com.julyday.shorturl.bean.ShortUrl;
import com.julyday.shorturl.service.IdGenerator;
import com.julyday.shorturl.service.ShortUrlConverter;
import com.julyday.shorturl.utils.BaseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/*
 * 默认短域名生成方法，短域名和长域名的映射关系真实生产可以存储在数据库中.
 */
@Component
public class DefaultShortUrlConverter implements ShortUrlConverter {
    private static final Logger log = LoggerFactory.getLogger(DefaultShortUrlConverter.class);
    private IdGenerator idGenerator;

    private ConcurrentMap<String, ShortUrl> shortUrlMap = new ConcurrentHashMap<>();

    public DefaultShortUrlConverter (IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    @Cacheable(value = "shortUrl", key = "#longUrl")
    public String generatorShortUrl(String longUrl) {
        Long id = idGenerator.nextId();
        String shortUrl = BaseUtils.encode10To62(id);
        ShortUrl bean = new ShortUrl(id, shortUrl, longUrl);
        shortUrlMap.put(shortUrl, bean);
        log.info("插入新的长域名:{}", longUrl);
        return shortUrl;
    }

    @Override
    public String getLongUrl(String shortUrl) {
        ShortUrl bean = shortUrlMap.get(shortUrl);
        return Objects.isNull(bean) ? null : bean.getLongUrl();
    }
}

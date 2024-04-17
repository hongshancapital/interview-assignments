package com.bolord.shorturl.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import com.bolord.shorturl.config.ShortUrlProperties;
import com.bolord.shorturl.exception.ShortUrlException;
import com.bolord.shorturl.generator.IdGenerator;
import com.bolord.shorturl.storage.UrlMappingStorage;
import com.bolord.shorturl.utils.Base62;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 短链接服务类
 */
@Slf4j
@Service
@AllArgsConstructor
public class ShortUrlService {

    private IdGenerator idGenerator;

    private UrlMappingStorage urlMappingStorage;

    private ShortUrlProperties shortUrlProperties;

    private static final long MAX_ID = 56800235583L;

    public String fromShortUrl(String base62Id) {
        final String url = urlMappingStorage.get(base62Id);

        log.info("[访问链接] base62Id={}, url={}", base62Id, url);

        return url;
    }

    public String toShortUrl(String url) {
        final long base10Id = idGenerator.generateId();

        // 限制最大自增 ID，超过后经过 Base62 会超过 6 个字符
        if (base10Id > MAX_ID) {
            throw new ShortUrlException(999, "存储已达到上限");
        }

        final String base62Id = Base62.encode(base10Id) + RandomStringUtils.randomAlphanumeric(2);
        urlMappingStorage.set(base62Id, url);

        log.info("[新增链接] base10Id={}, base62Id={}, url={}", base10Id, base62Id, url);

        return shortUrlProperties.getPrefix() + base62Id;
    }

}

package com.sequoia.domain.service.impl;

import com.sequoia.domain.exception.UrlNotExistException;
import com.sequoia.domain.service.IShortDomainService;
import com.sequoia.domain.service.IUrlCacheService;
import com.sequoia.domain.service.IdGenerator;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShortDomainServiceImplTest {
    @Test
    public void testShortDomain() {
        IdGenerator idGenerator = new SelfIncreaseIdGenerator(0);
        IUrlCacheService urlCacheService = new UrlCacheServiceImpl(8, 8);

        IShortDomainService domainService = new ShortDomainServiceImpl(idGenerator, urlCacheService);

        Set<String> urls = new HashSet<>();
        urls.add("http://www.baidu.com");
        urls.add("https://www.baidu.com");
        urls.add("https://www.baidu.com?param1=123");
        urls.add("https://www.baidu.com?param1=123&param2=456");

        urls.stream().map(url -> {
            String encodedUrl = Base64.getEncoder().encodeToString(url.getBytes(StandardCharsets.UTF_8));
            return domainService.toShortUrl(encodedUrl);
        }).forEach(shortUrl -> {
            String encodedShortUrl = Base64.getEncoder().encodeToString(shortUrl.getBytes(StandardCharsets.UTF_8));
            assertTrue(true, domainService.restoreToOriginUrl(encodedShortUrl));
        });

        String encodedShortUrl =
                Base64.getEncoder().encodeToString("https://www.google.com".getBytes(StandardCharsets.UTF_8));
        assertThrows(UrlNotExistException.class, () -> domainService.restoreToOriginUrl(encodedShortUrl));
    }
}
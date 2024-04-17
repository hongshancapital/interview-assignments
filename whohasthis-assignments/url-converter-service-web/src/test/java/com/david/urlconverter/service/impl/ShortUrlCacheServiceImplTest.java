package com.david.urlconverter.service.impl;

import com.david.urlconverter.service.web.IShortUrlLocalCacheService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ShortUrlCacheServiceImplTest {

    @Autowired
    IShortUrlLocalCacheService shortUrlLocalCacheService;

    private String shortUrl = "3e8832s8";

    private String longUrl = "http://www.david.com/url-converter";



    @Test
    public void testStoreShortToLongMapping() {
        shortUrlLocalCacheService.storeShortToLongMapping(shortUrl, longUrl);
        assertThat(shortUrlLocalCacheService.queryLongUrlInCache(shortUrl)).isEqualTo(longUrl);
    }

    @Test
    public void testQueryLongUrlInCache() {
        shortUrlLocalCacheService.storeShortToLongMapping(shortUrl, longUrl);
        assertThat(shortUrlLocalCacheService.queryLongUrlInCache(shortUrl)).isEqualTo(longUrl);
    }
}
package com.zc.shorturl.service.impl;

import com.zc.shorturl.service.ShortUrlService;
import com.zc.shorturl.snowflake.SnowflakeIdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.zc.shorturl.constant.ShortUrlConstant.SHORT_TO_LONG_CACHE_NAME;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ShortUrlServiceImplTest {

    @Autowired
    ShortUrlService shortUrlService;

    @Autowired
    CacheManager cacheManager;

    @Autowired
    SnowflakeIdGenerator snowflakeIdGenerator;

    private String shortUrlPrepare;

    @BeforeEach
    public void SetUp(){
        String longUrl = "http://www.baidu.com";
        shortUrlPrepare = shortUrlService.createShortUrl(longUrl);
        shortUrlService.updateShort2Long(shortUrlPrepare, longUrl);
    }

    @Test
    public void testCreateShortUrl() {
        assertThat(shortUrlService.createShortUrl("http://www.test1.com").length(), lessThanOrEqualTo(8));

        // test for : Current server system clock go back
        long t = System.currentTimeMillis() + 1000;
        snowflakeIdGenerator.setLastTimestamp(t);
        assertThat(shortUrlService.createShortUrl("http://www.test.com"), equalTo(""));
    }

    @Test
    public void testGetLongUrl() {
        assertThat(
                shortUrlService.getLongUrlFromCache(shortUrlPrepare),
                equalTo(cacheManager.getCache(SHORT_TO_LONG_CACHE_NAME).get(shortUrlPrepare, String.class))
        );

        assertThat(
                shortUrlService.getLongUrlFromCache("http://www.notexist.com"),
                equalTo("")
        );
    }

    @Test
    public void testUpdateShort2Long() {
        String longUrl = "https://github.com";
        String shortUrl = shortUrlService.createShortUrl(longUrl);
        shortUrlService.updateShort2Long(shortUrl, longUrl);
        assertThat(
                shortUrlService.getLongUrlFromCache(shortUrl),
                equalTo(cacheManager.getCache(SHORT_TO_LONG_CACHE_NAME).get(shortUrl, String.class))
        );
    }
}

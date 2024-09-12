package com.sequoia.web.mapper;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class ExpireMapTest {
    private static final String originalUrl = "http://www.baidu.com";
    private static final String shortUrl = "D6Mocpge";

    @Test
    public void hashMapUrlMapper() throws InterruptedException {
        UrlMapper mapper = new UrlMapper(UrlMapper.Strategy.trieMap, true, 2000,
                0, 1, TimeUnit.SECONDS);
        mapper.put(shortUrl, originalUrl);
        Assert.assertEquals(originalUrl, mapper.get(shortUrl));

        Thread.sleep(3000L);
        Assert.assertEquals(null, mapper.get(shortUrl));
    }
}

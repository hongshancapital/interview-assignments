package com.sequoia.web.mapper;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class UrlMapperTest {
    private static final String originalUrl = "http://www.baidu.com";
    private static final String shortUrl = "D6Mocpge";

    @Test
    public void hashMapUrlMapper(){
        UrlMapper mapper = new UrlMapper(UrlMapper.Strategy.hashMap, false, 0,
                0, 0, TimeUnit.DAYS);
        mapper.put(shortUrl, originalUrl);
        Assert.assertEquals(originalUrl, mapper.get(shortUrl));
        Assert.assertEquals(1, mapper.size());
    }

    @Test
    public void skipListMapUrlMapper(){
        UrlMapper mapper = new UrlMapper(UrlMapper.Strategy.skipListMap, false, 0,
                0, 0, TimeUnit.DAYS);
        mapper.put(shortUrl, originalUrl);
        Assert.assertEquals(originalUrl, mapper.get(shortUrl));
        Assert.assertEquals(1, mapper.size());
    }

    @Test
    public void trieMapUrlMapper(){
        UrlMapper mapper = new UrlMapper(UrlMapper.Strategy.trieMap, false, 0,
                0, 0, TimeUnit.DAYS);
        mapper.put(shortUrl, originalUrl);
        Assert.assertEquals(originalUrl, mapper.get(shortUrl));
        Assert.assertEquals(1, mapper.size());
    }
}

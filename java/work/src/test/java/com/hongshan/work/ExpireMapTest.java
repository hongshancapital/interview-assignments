package com.hongshan.work;

import com.hongshan.work.mapper.HashMapUrlMap;
import org.junit.Assert;
import org.junit.Test;
import java.util.concurrent.TimeUnit;

public class ExpireMapTest {
    private static final String longUrl = "http://www.baidu.com";
    private static final String shortUrl = "UxLcNz";

    @Test
    public void hashMapUrlMapper() throws InterruptedException {
        HashMapUrlMap mapper = new HashMapUrlMap(true, 2000,
                0, 1, TimeUnit.SECONDS);
        mapper.put(shortUrl, longUrl);
        Assert.assertEquals(longUrl, mapper.get(shortUrl));

        Thread.sleep(3000L);
        Assert.assertEquals(null, mapper.get(shortUrl));
    }
}
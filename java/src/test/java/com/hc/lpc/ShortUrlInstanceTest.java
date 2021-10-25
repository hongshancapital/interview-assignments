package com.hc.lpc;

import com.hs.lpc.ShortUrlInstance;
import org.junit.Assert;
import org.junit.Test;

public class ShortUrlInstanceTest {

    @Test
    public void shortUrlTest(){
        String longUrl = "https://blog.csdn.net/qq_31805885/article/details/86753069";
        for (String shortUrl : ShortUrlInstance.shortUrl(longUrl)) {
            System.out.println(shortUrl);
            Assert.assertEquals(shortUrl.length(),6);
        }
    }
}

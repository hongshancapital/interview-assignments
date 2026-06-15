package com.hongshan.work;

import com.hongshan.work.service.UrlService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UrlServiceImplTest {
    @Autowired
    private UrlService urlService;

    private static String longUrl = "www.baidu.com";

    @Test
    public void getLongUrlByShortUrl(){
        String shortUrl = urlService.getShortUrl(longUrl);
        System.out.println(shortUrl);
        String longUrl = urlService.getLongUrl(shortUrl);
        Assert.assertEquals(longUrl, longUrl);
    }

    @Test
    public void testExitLongUrl() {
        try {
            String shortUrl = urlService.getShortUrl(longUrl);
            String longUrl = urlService.getLongUrl(shortUrl);
            Assert.assertEquals(longUrl, longUrl);
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    @Test
    public void testGetLongUrl() {
        try {
            String shortUrl = urlService.getShortUrl(longUrl);
            String longUrl = urlService.getLongUrl(shortUrl);
            Assert.assertEquals(longUrl, longUrl);
        } catch (Exception e){
            e.printStackTrace();
        }
    }



}
package com.hs.lpc.tranurl;

import com.hs.lpc.tranurl.service.UrlService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlControllerTest {

    @Autowired
    private UrlService urlService;

    @Test
    public void testGetShortUrl(){
        String longUrl = "myitnews/p/12330297.html";
        String shortUrl = urlService.getShortUrl(longUrl);
        Assert.assertEquals(shortUrl.length(),6);
    }

    @Test
    public void testGetLongUrl(){
        String longUrl = "myitnews/p/12330297.html";
        String shortUrl = urlService.getShortUrl(longUrl);
        String longUrl1 = urlService.getLongUrl(shortUrl);
        Assert.assertEquals(longUrl,longUrl1);
    }


}

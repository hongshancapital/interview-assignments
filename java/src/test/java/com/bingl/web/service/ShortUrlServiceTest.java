package com.bingl.web.service;

import com.bingl.web.ApplicationTestBase;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ShortUrlServiceTest extends ApplicationTestBase {

    @Autowired
    private ShortUrlService shortUrlService;

    @Test
    public void shortUrlTest(){
        String longUrl="sdfweruoweutsdv/asdfsdoe.com";

        String shortUrl=shortUrlService.saveShortUrl(longUrl);

        Assert.assertEquals("wC9axiHV", shortUrl);

        try{
            String longUrl2=shortUrlService.readLongUrl(shortUrl);
            Assert.assertEquals("sdfweruoweutsdv/asdfsdoe.com", longUrl2);
        }
        catch (Exception e){}


    }

    @Test
    public void noLastShortUrlTest(){
        String longUrl="sdfweru";

        String shortUrl=shortUrlService.saveShortUrl(longUrl);

        Assert.assertEquals("pZkuG1cm", shortUrl);
    }
}

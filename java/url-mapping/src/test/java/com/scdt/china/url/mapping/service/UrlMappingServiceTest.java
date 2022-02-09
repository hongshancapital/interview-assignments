package com.scdt.china.url.mapping.service;


import com.scdt.china.url.mapping.Constants;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class UrlMappingServiceTest {

    @Autowired
    private IUrlMappingService urlMappingService;

    @Test
    public void testGetShortUrl(){
        String originalUrl = "https://github.com/scdt-china/interview-assignments-11";
        String shortUrl = urlMappingService.getShortUrl(originalUrl);
        Assert.assertTrue(shortUrl.startsWith(Constants.SHORT_URL_DOMAIN));
        Assert.assertEquals(shortUrl.length(),Constants.SHORT_URL_DOMAIN.length()+8);
    }


    @Test
    public void testGetOriginalUrl(){
        String originalUrl = "https://github.com/scdt-china/interview-assignments-22";
        String shortUrl = urlMappingService.getShortUrl(originalUrl);

        String returnOriginalUrl = urlMappingService.getOrinigalUrl(shortUrl);
        Assert.assertEquals(originalUrl,returnOriginalUrl);
    }
}

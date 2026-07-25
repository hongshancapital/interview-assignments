package com.url.controller;

import com.url.Main;
import com.url.serveice.UrlExchangeService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
public class ControllerTest {

    @Autowired
    private UrlExchangeService service;

    @Test
    public void mixedTest(){
        String shortUrl = service.getShort("www.baidu.com");
        Assert.assertTrue(shortUrl.length()<=8);
        String shortUrl2 = service.getShort("www.baidu.com");
        Assert.assertTrue(shortUrl2.length()<=8);
        String aShort = service.getShort("www.baidu.com");
        String longUrl = service.getLong(aShort);
        Assert.assertEquals(longUrl,"www.baidu.com");
    }

    @Test
    public void testGetShort(){
        String shortUrl = service.getShort("www.baidu.com");
        Assert.assertTrue(shortUrl.length()<=8);
        String shortUrl2 = service.getShort("www.baidu.com");
        Assert.assertTrue(shortUrl2.length()<=8);
    }

    @Test
    public void testGetLong(){
        String aShort = service.getShort("www.baidu.com");
        String longUrl = service.getLong(aShort);
        Assert.assertEquals(longUrl,"www.baidu.com");
    }

}

package org.example.sgenerate.service;

import org.example.sgenerate.model.UrlMappingInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IUrlGenerateServiceTest {

    @Autowired
    private IUrlGenerateService urlGenerateService;

    @Test
    public void generateShortUrl() {
        UrlMappingInfo info = urlGenerateService.generateShortUrl("http://baidu.com?a=100", null);
        System.out.println("create short url:" + info.getShortUrl());
        UrlMappingInfo url = urlGenerateService.readShortUrl(info.getShortUrl());
        System.out.println("create original url:" + url.getOriginalUrl() + " id:" + url.getId() + " isExpired:" + info.isExpired());
    }

    @Test
    public void generateShortUrlExpired() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        UrlMappingInfo info = urlGenerateService.generateShortUrl("http://baidu.com?a=100", sdf.parse("2020-12-01 12:00:00"));
        System.out.println("create short url:" + info.getShortUrl());
        UrlMappingInfo url = urlGenerateService.readShortUrl(info.getShortUrl());
        System.out.println("create original url:" + url.getOriginalUrl() + " id:" + url.getId() + " isExpired:" + info.isExpired());
    }

    @Test
    public void shortUrlNotFound() throws ParseException {
        UrlMappingInfo url = urlGenerateService.readShortUrl("https://s.cn/sK20uij");
        Assert.notNull(url);
    }
}
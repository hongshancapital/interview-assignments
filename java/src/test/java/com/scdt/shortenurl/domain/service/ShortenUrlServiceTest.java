package com.scdt.shortenurl.domain.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @Description
 * @Author chenlipeng
 * @Date 2022/3/1 4:24 下午
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ShortenUrlServiceTest {

    @Resource
    private ShortenUrlService shortenUrlService;

    @Test
    public void testGenShortenUrl() {
        String url = "www.baidu.com";
        String shortenUrl = shortenUrlService.genShortenUrl(url);
        Assert.assertEquals("qMnMze", shortenUrl);
    }

    @Test
    public void testGetOriginalUrl() {
        String shortenUrl = shortenUrlService.genShortenUrl("www.baidu.com");
        String originalUrl = shortenUrlService.getOriginalUrl("qMnMze");
        Assert.assertEquals(originalUrl, "www.baidu.com");
    }

}

package com.scdt.shortenurl.application;

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
public class ShortenUrlBizServiceTest {

    @Resource
    private ShortenUrlBizService shortenUrlBizService;

    @Test
    public void testGenShortenUrl() {
        String url = "www.baidu.com";
        String shortenUrl = shortenUrlBizService.genShortenUrl(url);
        Assert.assertEquals("qMnMze", shortenUrl);
    }

    @Test
    public void testGetOriginalUrl() {
        String url = "www.baidu.com";
        String shortenUrl = shortenUrlBizService.genShortenUrl(url);
        Assert.assertEquals("qMnMze", shortenUrl);
        String originalUrl = shortenUrlBizService.getOriginalUrl("qMnMze");
        Assert.assertEquals(url, originalUrl);
    }

}

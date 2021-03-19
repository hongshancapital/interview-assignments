package com.sxg.shortUrl.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.sxg.shortUrl.mapper.UrlMapper;
import com.sxg.shortUrl.model.UrlModel;
import com.sxg.shortUrl.service.UrlService;
import com.sxg.shortUrl.utils.RedisUtil;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("短链接服务层")
class UrlServiceTest {

    @Autowired
    private UrlService urlService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UrlMapper urlMapper;

    @Test
    @DisplayName("创建短链接")
    void createSUrl() {
        String s = "http://163.com";
        UrlModel urlModel = urlService.createShortUrl(s);
        Assertions.assertEquals(s, urlModel.getLongUrl());
    }


    @Test
    @DisplayName("获取长链接")
    void getUrl() {
        String s = "http://qq.com";
        UrlModel shortUrl = urlService.createShortUrl(s);
        UrlModel urlModel = urlService.getUrl(shortUrl.getShortUrl());
        Assertions.assertEquals(s, urlModel.getLongUrl());
        urlMapper.deleteUrl(shortUrl.getShortUrl());
        redisUtil.delete(RedisUtil.urlLong + shortUrl.getLongUrl());
        redisUtil.delete(RedisUtil.urlShort + shortUrl.getShortUrl());
    }

}
package com.example.surl.service;

import com.example.surl.entity.UrlDO;
import com.example.surl.mapper.UrlMapper;
import com.example.surl.utils.RedisUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

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
        String s = "http://126.com";
        UrlDO sUrl = urlService.createSUrl(s);
        Assertions.assertEquals(s, sUrl.getLurl());
    }


    @Test
    @DisplayName("获取长链接")
    void getUrl() {
        String s = "http://taobao.com";
        UrlDO sUrl = urlService.createSUrl(s);
        UrlDO url = urlService.getUrl(sUrl.getSurl());
        Assertions.assertEquals(s, url.getLurl());
        urlMapper.deleteUrl(sUrl.getSurl());
        redisUtil.delete(RedisUtil.preL + sUrl.getLurl());
        redisUtil.delete(RedisUtil.preS + sUrl.getSurl());
    }

}
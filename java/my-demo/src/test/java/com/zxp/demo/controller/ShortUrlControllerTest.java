package com.zxp.demo.controller;

import com.zxp.demo.MyDemoApplication;
import com.zxp.demo.service.ShortUrlChangeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @description: junit 测试类
 * @author: zxp
 * @date: Created in 2021/12/13 16:02
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MyDemoApplication.class)

public class ShortUrlControllerTest {

    @Resource
    ShortUrlChangeService shortUrlChangeService;

    @Test
    public void getShortUrl() {
       String longUrl="https://github.com/scdt-china/interview-assignments/tree/master/java";
       shortUrlChangeService.conversionShortUrl(longUrl);

    }

    @Test
    public void getLongUrl() {
        String shortUrl="http://a.cn/3";
        shortUrlChangeService.conversionLongUrl(shortUrl);
    }
}
package com.example.scdt.service.impl;

import com.example.scdt.service.UrlTransferService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlTransferServiceImplTest {

    @Autowired
    private UrlTransferService urlTransferService;

    private String longUrl = "www.baidu.com/idaifaehhafd/iwjfihaie3482642/jdiajfiaejifewhihio.jpg";

    @Test
    public void longUrlToShortUrl() {
        String shortUrl = urlTransferService.longUrlToShortUrl(longUrl);
        System.out.println("生成的短域名：" + shortUrl);
        String longUrlAfter = urlTransferService.shortUrlToLongUrl(shortUrl);
        System.out.println("短域名映射的长域名：" + longUrlAfter);
        Assert.assertEquals(longUrl, longUrlAfter);
    }
}
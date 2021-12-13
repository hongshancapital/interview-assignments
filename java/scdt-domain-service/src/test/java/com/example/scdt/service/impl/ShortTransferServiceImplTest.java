package com.example.scdt.service.impl;

import com.example.scdt.exception.BusinessException;
import com.example.scdt.service.IShortUrlTransferService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author JonathanCheung
 * @date 2021/12/13 10:47
 * @describe
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShortTransferServiceImplTest {


    @Autowired
    private IShortUrlTransferService shortUrlTransferService;

    private String longUrl = "https://www.baidu.com/img/PCfb_5bf082d29588c07f842ccde3f97243ea.png";

    @Test
    public void longUrlToShortUrl() throws BusinessException {
        String shortUrl = shortUrlTransferService.longUrlToShortUrl(longUrl);
        System.out.println("生成的短域名：" + shortUrl);
        String longUrlAfter = shortUrlTransferService.shortUrlToLongUrl(shortUrl);
        System.out.println("短域名映射的长域名：" + longUrlAfter);
        Assert.assertEquals(longUrl, longUrlAfter);
    }
}

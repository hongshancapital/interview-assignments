package com.sequoia.shorturl.service;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import com.sequoia.shorturl.common.SequoiaResponse;
/***
 *
 *@ DESC
 *
 *@Author xiaojun
 *
 *@Date 2021/6/27 23:50
 *
 *@version v1.0
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShortUrlServiceImplTest {
    @Autowired
    ShortUrlService shortUrlService;

    @Test
    public void testGetShortUrlAndGetOriginalUrl() {
        String originUrl="https://zhuanlan.zhihu.com/p/194199097";
        SequoiaResponse response=shortUrlService.getShortUrl(originUrl);
        System.out.println(response.getData().toString());
        String shortUrl=response.getData().toString();
        SequoiaResponse response2=shortUrlService.getOriginalUrl(shortUrl);
        String originUrl2=response2.getData().toString();
        assertEquals(originUrl,originUrl2);
    }

    @Test
    public void testGetShortUrl() {
        SequoiaResponse response=shortUrlService.getShortUrl(null);
        assertEquals(response.getData(),null);
    }

    @Test
    public void testGetOriginalUrl(){
        SequoiaResponse response=shortUrlService.getOriginalUrl(null);
        assertEquals(response.getData(),null);
    }

}

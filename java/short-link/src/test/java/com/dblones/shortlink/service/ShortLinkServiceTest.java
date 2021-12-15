package com.dblones.shortlink.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShortLinkServiceTest {

    @Autowired
    ShortLinkService shortLinkService;

    @Test
    public void transformUrl(){
        String url = "http://www.dblones.com/index.html";
        String shortUrl = shortLinkService.transformUrl(url);
        System.out.println(shortUrl);
    }

    @Test
    public void transformUrlMaxLength(){
        String url = "http://www.dblones.com/index.html";
        for(int i = 0; i < 100; i++) {
            String shortUrl = shortLinkService.transformUrl(url + "?p=" + i);
            System.out.println(shortUrl);
        }
    }


    @Test
    public void getLongUrl(){
        String url = "s.cn/s";
        String shortUrl = shortLinkService.getLongUrl(url);
        System.out.println(shortUrl);
    }
}

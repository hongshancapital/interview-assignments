package com.shortlink.service;

import com.shortlink.ShortLinkApplication;
import com.shortlink.common.BusinessException;
import com.shortlink.common.ShortLinkCache;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ShortLinkApplication.class)
@Slf4j
public class ShortLinkServiceTest {
    @Autowired
    private ShortLinkServiceImpl shortLinkService;

    @Test
    public void testSaveAndGetShortLink(){
        String longLink = "https://www.baidu.com";
        String shortLink = shortLinkService.saveShortLink(longLink);
        Assert.assertTrue(longLink.equals(shortLinkService.convert2LongLink(shortLink)));
    }

    @Test(expected = BusinessException.class)
    public void testEmptyError(){
        shortLinkService.saveShortLink(null);
    }

    @Test(expected = BusinessException.class)
    public void testNotHit(){
        shortLinkService.convert2LongLink("123");
    }


    @Test(expected = BusinessException.class)
    public void testTooLongError(){
        StringBuilder longLinkBuilder = new StringBuilder();
        for (int i = 0; i < 1025; i++) {
            longLinkBuilder.append("1");
        }
        shortLinkService.saveShortLink(longLinkBuilder.toString());
    }

    @Test(expected = BusinessException.class)
    public void testLongLinkEmptyError(){
        shortLinkService.convert2LongLink(null);
    }
}

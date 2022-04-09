package com.ryr.homework.controller;

import com.ryr.homework.common.util.ShortUrlConvertUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ShortUrlControllerTest {

    @Autowired
    private ShortUrlController shortUrlController;

    @Test
    public void testShortUrlController() {
        //需要保存的长域名
        String originLongUrl = "https://www.baidu.com";
        //第一次保存返回的短域名
        String shortUrl1 = shortUrlController.getShorUrl(originLongUrl);
        String shortStr1 = shortUrl1.substring(shortUrl1.lastIndexOf("/") + 1);
        //第一次返回的短域名获取到的长域名
        String returnLongUrl = shortUrlController.getLongUrl(shortStr1);
        //期望与原长域名一致
        Assert.assertEquals(originLongUrl, returnLongUrl);
        //再保存一次
        //第二次保存返回的短域名
        String shortUrl2 = shortUrlController.getShorUrl(originLongUrl);
        //期待两次返回短域名一致
        Assert.assertEquals(shortUrl1, shortUrl2);

        //获取不存在的短域名
        String shortUrl3 = shortUrlController.getLongUrl("ttttttttt");
        Assert.assertEquals("无效的短域名",shortUrl3);
    }
}

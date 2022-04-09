package com.ryr.homework.controller;

import com.ryr.homework.common.util.ShortUrlConvertUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.when;

/**
 * 短域名冲突场景测试
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ShortUrlConflictTest {

    @Autowired
    private ShortUrlController shortUrlController;

    @MockBean
    private ShortUrlConvertUtil shortUrlConvertUtil;

    /**
     * 测试返回重复短域名的场景
     */
    @Test
    public void testHashConflict() {

        //需要保存的长域名
        String originLongUrl1 = "https://www.baidu.com";
        String originLongUrl2 = "https://cn.bing.com";

        //模拟生成同一短链
        when(shortUrlConvertUtil.getShortUrl(originLongUrl1)).thenReturn("abcdefgh");
        when(shortUrlConvertUtil.getShortUrl(originLongUrl2)).thenReturn("abcdefgh");

        //第一次保存返回的短域名
        String shortUrl1 = shortUrlController.getShorUrl(originLongUrl1);

        when(shortUrlConvertUtil.getShortUrl("https://cn.bing.com_mul")).thenReturn("abcdefgh");
        when(shortUrlConvertUtil.getShortUrl("https://cn.bing.com_mul_mul")).thenReturn("123456");
        //第二次保存返回的短域名 mock了短链冲突
        String shortUrl2 = shortUrlController.getShorUrl(originLongUrl2);
        Assert.assertNotEquals(shortUrl1, shortUrl2);

        //第三次保存返回的短域名 mock了短链冲突 返回第二次保存的短链
        String shortUrl3 = shortUrlController.getShorUrl(originLongUrl2);
        Assert.assertEquals(shortUrl2, shortUrl3);

        //第四次保存 mock了三次冲突 最后返回失败
        when(shortUrlConvertUtil.getShortUrl("https://cn.bing.com_mul_mul")).thenReturn("abcdefgh");
        when(shortUrlConvertUtil.getShortUrl("https://cn.bing.com_mul_mul_mul")).thenReturn("abcdefgh");
        String shortUrl4 = shortUrlController.getShorUrl(originLongUrl2);
        Assert.assertEquals("生成短链失败", shortUrl4);

    }
}

package com.example.baiyang.demo;

import com.example.baiyang.demo.service.ShortUrlService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: baiyang.xdq
 * @date: 2021/12/17
 * @description: 短域名服务接口实现类单元测试
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ShortUrlServiceImplTest{

    @Autowired
    private ShortUrlService shortUrlService;

    @Test
    public void testGetLongUrl() {
        Assert.assertEquals("获取长域名结果有误", "http://www.sequoiacap.cn/china/people/introduction/", shortUrlService.getLongUrl("Zveqyq"));
    }

    @Test
    public void testGetShortUrl() {
        Assert.assertEquals("获取短域名结果有误", "Zveqyq", shortUrlService.getShortUrl("http://www.sequoiacap.cn/china/people/introduction/","MD5"));
    }

    @Test
    public void testGetShortUrlNotInCache() {
        Assert.assertEquals("获取短域名结果有误", "jMrMBv", shortUrlService.getShortUrl("http://www.sequoiacap.cn/china/people/introduction/211","MD5"));
    }
}

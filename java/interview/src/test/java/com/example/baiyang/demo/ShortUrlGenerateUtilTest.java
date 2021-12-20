package com.example.baiyang.demo;

import com.example.baiyang.demo.utils.ShortUrlGenerateUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: baiyang.xdq
 * @date: 2021/12/17
 * @description: 短域名算法单元测试
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ShortUrlGenerateUtilTest {

    @Test
    public void testGetRandomShortUrl() {
        Assert.assertEquals("短域名生成结果有误", "Zveqyq", ShortUrlGenerateUtil.getRandomShortUrl("http://www.sequoiacap.cn/china/people/introduction/", "MD5"));
    }

    @Test
    public void testGetRandomShortUrlWithException() {
        Assert.assertEquals("短域名生成结果有误", "", ShortUrlGenerateUtil.getRandomShortUrl("http://www.sequoiacap.cn/china/people/introduction/", "baiyang"));
    }
}

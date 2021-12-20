package com.example.baiyang.demo;

import com.example.baiyang.demo.domain.UrlEntity;
import com.example.baiyang.demo.utils.ShortUrlUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: baiyang.xdq
 * @date: 2021/12/17
 * @description: 域名服务工具类单元测试
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ShortUrlUtilTest{
    @Autowired
    private ShortUrlUtil shortUrlUtil;

    @Test
    public void testSave() {
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setLongUrl("http://www.sequoiacap.cn/china/people/introduction/");
        urlEntity.setShortUrl("Zveqyq");

        Assert.assertTrue("存储域名映射信息失败", shortUrlUtil.save(urlEntity));
    }

    @Test
    public void testGetLongUrl() {
        Assert.assertEquals("获取长域名结果有误", "http://www.sequoiacap.cn/china/people/introduction/", shortUrlUtil.getLongUrl("Zveqyq"));
    }

    @Test
    public void testGetShortUrl() {
        Assert.assertEquals("获取短域名结果有误", "Zveqyq", shortUrlUtil.getShortUrl("http://www.sequoiacap.cn/china/people/introduction/"));
    }
}

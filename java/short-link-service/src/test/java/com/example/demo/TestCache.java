package com.example.demo;

import com.example.demo.bean.po.ShortUrlPo;
import com.example.demo.bean.response.ShortLinkResponse;
import com.example.demo.service.ShortLinkCache;
import com.example.demo.service.ShortLinkService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author shenbing
 * @since 2022/1/8
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestCache {

    @Resource
    ShortLinkCache shortLinkCache;
    @Resource
    ShortLinkService shortLinkService;

    @Test
    public void testGen() {
        ShortUrlPo po1 = shortLinkCache.next();
        shortLinkCache.add(po1.getCode(), po1);
        ShortUrlPo po2 = shortLinkCache.getShortUrl(po1.getCode());
        Assert.assertEquals(po1, po2);
    }

    @Test
    public void testSave() {
        String originalUrl = "www.baidu.com";
        ShortLinkResponse response = shortLinkService.register(originalUrl);
        Assert.assertEquals(response.getOriginalUrl(), originalUrl);
    }

}

package com.demo.domain;

import com.demo.domain.controller.DomainController;
import com.demo.domain.util.Constant;
import com.demo.domain.util.ShortUrlUtil;
import com.demo.domain.util.SpringBeanUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

@SpringBootTest()
@RunWith(SpringJUnit4ClassRunner.class)
class DomainApplicationTests {

    @Autowired
    DomainController domainController;

    @Before
    public void before() {

    }

    @Test
    public void testShortUrlUtil() {
        String shortUrl12 = "uqUJRr";
        String shortUrl22 = "vUJbEr";
        ShortUrlUtil.DOMAIN_URL_MAP.put(shortUrl12, "http://dafaddadf/sss");
        ShortUrlUtil.DOMAIN_URL_MAP.put(shortUrl22, "http://dafaddadf/sss2");
        String longUrl = "http://www.baidu.com";
        String shortUrl = ShortUrlUtil.getShortUrl(longUrl, null);
        String shortUrl2 = ShortUrlUtil.getShortUrl(longUrl, null);
        Assert.assertEquals(shortUrl, shortUrl2);
        System.out.println(shortUrl);
        String longUrl1 = ShortUrlUtil.getLongUrl(shortUrl);
        System.out.println(longUrl);
        System.out.println(ShortUrlUtil.size());
        Assert.assertEquals(longUrl1, longUrl);
        ShortUrlUtil.DOMAIN_URL_MAP.remove(shortUrl2);
    }

    @Test
    public void testShortUrlUtilBeforeInsert() {
        String longUrl = "http://www.baidu.com";
        String shortUrl = ShortUrlUtil.getShortUrl(longUrl, null);
        String shortUrl2 = ShortUrlUtil.getShortUrl(longUrl, null);
        Assert.assertEquals(shortUrl, shortUrl2);
        System.out.println(shortUrl);
        String longUrl1 = ShortUrlUtil.getLongUrl(shortUrl);
        System.out.println(longUrl);
        System.out.println(ShortUrlUtil.size());
        Assert.assertEquals(longUrl1, longUrl);
    }


    @Test
    public void testShortUrlUtilEncrypt() {
        String shortUrl = ShortUrlUtil.encrypt(null);
        Assert.assertEquals("", ShortUrlUtil.encrypt(null));
    }

    @Test
    public void testShortUrlUtil2() {
        String longUrl = "";
        String shortUrl = ShortUrlUtil.getShortUrl(longUrl, null);
        System.out.println(shortUrl);
        String longUrl1 = ShortUrlUtil.getLongUrl(shortUrl);
        System.out.println(ShortUrlUtil.size());
        System.out.println(longUrl);
        Assert.assertEquals(longUrl1, longUrl);
    }

    @Test
    public void testShortUrlUtil3() {
        String longUrl = "http://www.baidu.com";
        String shortUrl = ShortUrlUtil.getShortUrl(longUrl, UUID.randomUUID().toString());
        System.out.println(shortUrl);
        String longUrl1 = ShortUrlUtil.getLongUrl(shortUrl);
        System.out.println(ShortUrlUtil.size());
        System.out.println(longUrl);
        Assert.assertEquals(longUrl1, longUrl);
    }


    @Test
    public void testApi() {
        String longUrl = "http://www.baidu.com/abc";
        String shortUrl = domainController.getShortUrl(longUrl);
        String longUrl1 = domainController.getLongUrl(shortUrl);
        Assert.assertEquals(longUrl1, longUrl);
    }

    @Test
    public void testApi2() {
        String longUrl = "";
        String shortUrl = domainController.getShortUrl(longUrl);
        System.out.println(shortUrl);
        Assert.assertEquals(Constant.URL_PARSE_ERROR, shortUrl);
        shortUrl = "";
        longUrl = domainController.getLongUrl(shortUrl);
        Assert.assertEquals(Constant.URL_PARSE_ERROR, longUrl);
        longUrl = null;
        shortUrl = domainController.getShortUrl(longUrl);
        System.out.println(shortUrl);
        Assert.assertEquals(Constant.URL_PARSE_ERROR, shortUrl);
        shortUrl = null;
        longUrl = domainController.getLongUrl(shortUrl);
        Assert.assertEquals(Constant.URL_PARSE_ERROR, longUrl);
    }

    @Test
    public void testBean() {
        DomainController domainController2 = SpringBeanUtil.getBean("domainController", DomainController.class);
        Assert.assertEquals(domainController, domainController2);
    }


}

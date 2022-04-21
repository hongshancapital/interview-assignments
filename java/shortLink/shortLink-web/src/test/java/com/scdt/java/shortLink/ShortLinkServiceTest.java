package com.scdt.java.shortLink;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.scdt.java.shortLink.web.controller.ShortLinkController;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringbootApplication.class)
public class ShortLinkServiceTest {

    @Resource
    ShortLinkController shortLinkController;

    @Test
    public void testIdempotentOfGetShortLink() {
        String link1 = shortLinkController.getShortLink("www.baidu.com").getContent();
        String link2 = shortLinkController.getShortLink("www.baidu.com").getContent();
        Assert.assertEquals(link1, link2);
    }

    @Test
    public void testRestoreLongLink() {
        String shortLink = shortLinkController.getShortLink("www.baidu.com").getContent();
        Assert.assertEquals("www.baidu.com", shortLinkController.restoreLongLink(shortLink).getContent());
        Assert.assertEquals(400, shortLinkController.restoreLongLink("QQJJ").getStatus());
    }

    @Test
    public void testEmptyInput() {
        Assert.assertEquals(400, shortLinkController.getShortLink("").getStatus());
        Assert.assertEquals(400, shortLinkController.restoreLongLink("").getStatus());
    }
}
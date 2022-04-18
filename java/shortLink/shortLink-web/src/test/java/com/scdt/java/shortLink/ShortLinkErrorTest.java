package com.scdt.java.shortLink;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.scdt.java.shortLink.component.service.ShortLinkService;
import com.scdt.java.shortLink.web.controller.ShortLinkController;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringbootApplication.class)
public class ShortLinkErrorTest {

    @MockBean
    ShortLinkService shortLinkService;
    @Resource
    ShortLinkController shortLinkController;

    @Test
    public void testCacheError() {
        when(shortLinkService.getShortLink(Mockito.anyString())).thenThrow(new RuntimeException());
        when(shortLinkService.restoreLongLink(Mockito.anyString())).thenThrow(new RuntimeException());
        Assert.assertEquals(500, shortLinkController.getShortLink("www.baidu.com").getStatus());
        Assert.assertEquals(500, shortLinkController.restoreLongLink("www.baidu.com").getStatus());
    }
}
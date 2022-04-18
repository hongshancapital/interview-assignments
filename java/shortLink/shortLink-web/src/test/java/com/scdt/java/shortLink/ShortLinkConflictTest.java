package com.scdt.java.shortLink;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.scdt.java.shortLink.component.util.ShortLinkUtil;
import com.scdt.java.shortLink.web.controller.ShortLinkController;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringbootApplication.class)
public class ShortLinkConflictTest {

    @MockBean
    ShortLinkUtil shortLinkUtil;
    @Resource
    ShortLinkController shortLinkController;

    @Test
    public void testConflict() {
        when(shortLinkUtil.generateShortLink(Mockito.anyString(), anyInt())).thenReturn("aabbcc", "fewon");
        when(shortLinkUtil.existShortLink("aabbcc")).thenReturn(true);
        Assert.assertEquals(200, shortLinkController.getShortLink("www.baidu.com").getStatus());
        when(shortLinkUtil.generateShortLink(Mockito.anyString(), anyInt())).thenReturn("aabbccdd");
        when(shortLinkUtil.existShortLink(Mockito.anyString())).thenReturn(true);
        Assert.assertEquals(400, shortLinkController.getShortLink("www.taobao.com").getStatus());
    }
}
package com.hongshan.link.service;

import com.hongshan.link.service.controller.LinkController;
import com.hongshan.link.service.entity.Result;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(value = SpringRunner.class)
@SpringBootTest(classes = LinkChangeApplication.class)
public class LinkControllerTests {

    @Autowired
    private LinkController linkController;

    @Test
    public void testGetShortLinkInvalidParam(){
        Result<String> shortLink1 = linkController.getShortLink(null);
        Assert.assertEquals(0,shortLink1.getCode());
        String url="abc";
        Result<String> shortLink2 = linkController.getShortLink(url);
        Assert.assertEquals(0,shortLink2.getCode());
    }


    @Test
    public void testGetShortLink(){
        String url="http://www.xx.com";
        Result<String> shortLink = linkController.getShortLink(url);
        Assert.assertNotNull(shortLink.getData());
    }

    @Test
    public void testGetLongLinkInvalidParam(){
        Result<String> shortLink1 = linkController.getLongLink("abc");
        Assert.assertEquals(0,shortLink1.getCode());
        String url="http://www.xx.com";
        Result<String> shortLink2 = linkController.getLongLink(url);
        Assert.assertEquals(0,shortLink2.getCode());
    }

    @Test
    public void testGetLongLink(){
        String url="http://www.xx.com";
        Result<String> shortLink1 = linkController.getShortLink(url);
        Result<String> longLink = linkController.getLongLink(shortLink1.getData());
        Assert.assertEquals(url,longLink.getData());
    }


    @Test
    public void testGetShortLinkByMap(){
        String url="https://hongshan.xx.com/000000";
        Result<String> longLink = linkController.getLongLink(url);
        Assert.assertNull(longLink.getData());
    }


}

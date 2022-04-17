package com.scdt.shortlink.service;

import com.scdt.shortlink.service.LinkService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xbhong
 * @date 2022/4/16 21:13
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class LinkServiceTest {

    @Autowired
    private LinkService linkService;

    private static String longLinkOrigin = "https://github.com/scdt-china/interview-assignments/tree/master/java";

    @Test
    public void getLongLinkTest() {
        try {
            String shortlink = linkService.createShortLink(longLinkOrigin);
            Assert.assertNotNull(shortlink);
            String longLink = linkService.getLongLink(shortlink);
            Assert.assertNotNull(longLink);
            Assert.assertEquals(longLink, longLinkOrigin);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

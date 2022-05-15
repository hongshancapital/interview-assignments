package org.demo.shortlink.service.impl;

import org.demo.shortlink.service.ShortLinkService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author wsq
 * @date 2022/3/26 002619:02
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class ShortLinkServiceImplTest {
    @Resource
    ShortLinkService shortLinkService;

    @Test
    void generateShortLink() {
        String shortLink = shortLinkService.generateShortLink("http://www.baidu.com?sfe=3&few=3");
        assertEquals("http://www.baidu.com?sfe=3&few=3", shortLinkService.findLongLink(shortLink));
        shortLinkService.generateShortLink("www.baidu.com?sfe=3&few=3");

    }

    @Test
    void generateShortLinkNull() {
        shortLinkService.generateShortLink(null);
        shortLinkService.generateShortLink("");
    }

    @Test
    void findLongLink() {

        assertNull(shortLinkService.findLongLink("xnd653sd"));
        shortLinkService.findLongLink(null);
        shortLinkService.findLongLink("");

    }
}
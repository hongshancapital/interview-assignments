package com.zoujing.shortlink.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LinkGeneratorServiceTest {
    @Resource
    private LinkGeneratorService linkGeneratorService;

    @Test
    public void testLinkGeneratorService() {
        assertNull(linkGeneratorService.getShortLink(123l, null));
        assertNull(linkGeneratorService.getLongLink(123l, null));
        assertNotNull(linkGeneratorService.getShortLink(123l, "xxxxxxxxxxxx.xxx.com"));
        assertEquals("xxxxxxxxxxxx.xxx.com"
                , linkGeneratorService.getLongLink(123l,
                        linkGeneratorService.getShortLink(123l, "xxxxxxxxxxxx.xxx.com")));
    }
}

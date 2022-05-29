package com.wuaping.shortlink.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 短域名单元测试
 *
 * @author Aping
 * @since 2022/3/20 14:25
 */
@SpringBootTest
public class ShortLinkServiceTest {

    @Autowired
    private ShortLinkService shortLinkService;

    @Test
    void shortLinkTest() {

        String originalLink = "https://github.com/scdt-china/interview-assignments/tree/master/java";

        String shortLink = shortLinkService.toShortLink(originalLink);

        System.out.println("shortLink: " + shortLink);

        String originalLinkCache = shortLinkService.originalLink(shortLink);

        if (!originalLink.equals(originalLinkCache)){
            throw new RuntimeException("toShortLink test failure");
        }

        System.out.println("test pass");

    }

}

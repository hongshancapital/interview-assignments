package com.scdt.demo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = ServiceConfig.class)
public class ServiceTest {
    private static final String DOMAIN = "https://scdt.cn/";

    @Autowired
    private ShortURLService shortURLService;

    @Test
    public void shortUrlTest() {
        String origin = "https://tinyurl.com/app/myurls";
        String url = shortURLService.shortenUrl(origin);
        assertNotNull(url);
        url = url.replaceAll(DOMAIN, "");
        Optional<String> optional = shortURLService.getOriginUrl(url);
        assertNotNull(optional.orElse(null));
    }
}

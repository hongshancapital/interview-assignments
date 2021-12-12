package com.scdt.assignment.application;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShortURLApplicationServiceTest {

    private ShortURLApplicationService service = new ShortURLApplicationService();
    @Test
    public void testUrl() {
        String url = "www.baidu.com";
        String shortUrl = service.getShortUrl(url);
        assertEquals(url, service.getOriginUrl(shortUrl));

        url = "";
        shortUrl = service.getShortUrl(url);
        assertEquals(url, service.getOriginUrl(shortUrl));

        url = null;
        shortUrl = service.getShortUrl(url);
        assertEquals(url, service.getOriginUrl(shortUrl));

        url = "https://www.baidu.com.cn/post/6844903853830176776";
        shortUrl = service.getShortUrl(url);
        assertEquals(url, service.getOriginUrl(shortUrl));
    }
}
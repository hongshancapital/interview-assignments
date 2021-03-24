package com.snail.shorturlservice;

import com.snail.shorturlservice.service.ShortUrlService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShortUrlTest {

    @Autowired
    private ShortUrlService shortUrlService;

    @Test
    void testShortUrl() throws Exception {
        String url = "https://news.sina.com.cn/gov/xlxw/2021-03-23/doc-ikknscsi9992821.shtml";
        String shortUrl = shortUrlService.shorten(url);
        System.out.println(shortUrl);

        String shortUrlKey = "ZM5Bcv";
        String longUrl = shortUrlService.getLongUrl(shortUrlKey);
        System.out.println(longUrl);

        String url1 = "https://news.sinaeeee.com.cn/gov/xlxw/2021-03-23/doc-ikknscsi9992821.shtml";
        String shortUrl1 = shortUrlService.shorten(url1);
        System.out.println(shortUrl1);
    }
}
package com.polly.shorturl.tools;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author polly
 * @date 2022.03.24 13:25:17
 */
@SpringBootTest
public class ShortUrlhandlerTest {
    @Autowired
    private ShortUrlHandler handler;

    @Test
    public void insert() {
        System.out.println(handler.insert("test"));
    }

    @Test
    public void getByShortUrl() {
        handler.getByShortUrl(handler.insert("test"));
    }
}

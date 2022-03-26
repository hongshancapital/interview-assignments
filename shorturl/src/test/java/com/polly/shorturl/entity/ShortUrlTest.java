package com.polly.shorturl.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author polly
 * @date 2022.03.26 20:09:38
 */
@SpringBootTest
public class ShortUrlTest {

    private final ShortUrl shortUrl = new ShortUrl();

    @Test
    public void setId() {
        shortUrl.setId(1L);
    }

    @Test
    public void getId() {
        shortUrl.getId();
    }

    @Test
    public void setUrl() {
        shortUrl.setUrl("test");
    }

    @Test
    public void getUrl() {
        shortUrl.getUrl();
    }

}

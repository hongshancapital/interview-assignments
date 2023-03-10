package com.github.shwas1.shorturl.dao;

import com.github.shwas1.shorturl.ShortUrlApplicationTests;
import com.github.shwas1.shorturl.model.ShortUrlPO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ShortUrlMemoryDAOTest extends ShortUrlApplicationTests {
    @Autowired
    private ShortUrlMemoryDAO shortUrlMemoryDAO;


    @Test
    void notIdempotent() {
        shortUrlMemoryDAO.setIdempotent(false);
        ShortUrlPO shortUrlPO1 = new ShortUrlPO();
        shortUrlPO1.setOriginalUrl("https://www.baidu.com/");
        shortUrlPO1.setShortUrl("https://t.tn/aaaaaaaa");
        ShortUrlPO save1 = shortUrlMemoryDAO.save(shortUrlPO1);

        ShortUrlPO shortUrlPO2 = new ShortUrlPO();
        shortUrlPO2.setOriginalUrl("https://www.baidu.com/");
        shortUrlPO2.setShortUrl("https://t.tn/bbbbbbbb");
        ShortUrlPO save2 = shortUrlMemoryDAO.save(shortUrlPO2);
        assertNotEquals(save1.getShortUrl(), save2.getShortUrl());
    }

    @Test
    void idempotent() {
        shortUrlMemoryDAO.setIdempotent(true);
        ShortUrlPO shortUrlPO1 = new ShortUrlPO();
        shortUrlPO1.setOriginalUrl("https://www.baidu.com/");
        shortUrlPO1.setShortUrl("https://t.tn/aaaaaaaa");
        ShortUrlPO save1 = shortUrlMemoryDAO.save(shortUrlPO1);

        ShortUrlPO shortUrlPO2 = new ShortUrlPO();
        shortUrlPO2.setOriginalUrl("https://www.baidu.com/");
        shortUrlPO2.setShortUrl("https://t.tn/bbbbbbbb");
        ShortUrlPO save2 = shortUrlMemoryDAO.save(shortUrlPO2);
        assertEquals(save1.getShortUrl(), save2.getShortUrl());
    }

    @Test
    void idempotent2() {
        shortUrlMemoryDAO.setIdempotent(true);
        ShortUrlPO shortUrlPO1 = new ShortUrlPO();
        shortUrlPO1.setOriginalUrl("https://www.baidu.com/");
        shortUrlPO1.setShortUrl("https://t.tn/aaaaaaaa");
        ShortUrlPO save1 = shortUrlMemoryDAO.save(shortUrlPO1);

        ShortUrlPO shortUrlPO2 = new ShortUrlPO();
        shortUrlPO2.setOriginalUrl("https://www.baidu.com/2");
        shortUrlPO2.setShortUrl("https://t.tn/bbbbbbbb");
        ShortUrlPO save2 = shortUrlMemoryDAO.save(shortUrlPO2);
        assertNotEquals(save1.getShortUrl(), save2.getShortUrl());
    }
}
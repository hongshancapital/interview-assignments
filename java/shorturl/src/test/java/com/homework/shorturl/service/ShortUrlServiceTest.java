package com.homework.shorturl.service;

import com.homework.shorturl.model.LongShortMapModel;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class ShortUrlServiceTest {

    @Autowired
    private ShortUrlService service;

    @Test
    void create() {
        for (int i = 0; i < 5; i++) {
            ResponseEntity<LongShortMapModel> resp = createShortUrl(randomLongUrl());
            Assertions.assertNotNull(resp.getBody());
            Assertions.assertEquals(String.valueOf(i), resp.getBody().getShortUrl());
        }
    }

    private ResponseEntity<LongShortMapModel> createShortUrl(String longUrl) {
        LongShortMapModel req1 = new LongShortMapModel();
        req1.longUrl(longUrl);
        return service.create(req1);
    }

    @Test
    void queryLongUrl() {
    }

    private String randomLongUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append("http://");
        sb.append(RandomStringUtils.random(RandomUtils.nextInt(1, 128)));
        sb.append("/");
        sb.append(RandomStringUtils.random(RandomUtils.nextInt(1, 1024)));
        sb.append("?");
        sb.append(RandomStringUtils.random(RandomUtils.nextInt(1, 1024)));
        sb.append("=");
        sb.append(RandomStringUtils.random(RandomUtils.nextInt(1, 1024)));
        return sb.toString();
    }
}
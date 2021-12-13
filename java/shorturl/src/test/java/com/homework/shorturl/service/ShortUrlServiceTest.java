package com.homework.shorturl.service;

import com.homework.shorturl.model.LongShortMapModel;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ShortUrlServiceTest {

    @Autowired
    private ShortUrlService service;

    private static final int INITIAL_CAPACITY = 10;
    private static final ArrayList<String> longUrls = new ArrayList<>(INITIAL_CAPACITY);

    @BeforeAll
    static void initLongUrls() {
        for (int i = 0; i < INITIAL_CAPACITY; i++) {
            longUrls.add(randomLongUrl());
        }
    }

    @Test
    @Order(1)
    void create_expectIncrementId_whenCreateShortUrlOneByOne() {
        for (int i = 0; i < longUrls.size(); i++) {
            String longUrl = longUrls.get(i);
            LongShortMapModel req = new LongShortMapModel();
            req.longUrl(longUrl);
            ResponseEntity<LongShortMapModel> resp = service.create(req);
            Assertions.assertNotNull(resp.getBody());
            Assertions.assertEquals(String.valueOf(i), resp.getBody().getShortUrl());
        }
    }

    @Test
    @Order(2)
    void queryLongUrl_expectLongUrl_whenQueryByShortUrl() {
        for (int i = 0; i < longUrls.size(); i++) {
            ResponseEntity<LongShortMapModel> resp = service.queryLongUrl(String.valueOf(i));
            Assertions.assertNotNull(resp.getBody());
            Assertions.assertEquals(longUrls.get(i), resp.getBody().getLongUrl());
        }
    }

    private static String randomLongUrl() {
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
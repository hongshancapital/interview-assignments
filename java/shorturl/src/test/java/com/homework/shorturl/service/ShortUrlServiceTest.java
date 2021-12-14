package com.homework.shorturl.service;

import com.homework.shorturl.model.LongShortMapModel;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ShortUrlServiceTest {

    @Autowired
    private ShortUrlService service;

    private static final int INITIAL_CAPACITY = 10;
    private static final ArrayList<String> longUrls = new ArrayList<>(INITIAL_CAPACITY);
    private static final int MAX_MAP_LIMIT = 1_000;


    @BeforeAll
    static void initLongUrls() {
        for (int i = 0; i < INITIAL_CAPACITY; i++) {
            longUrls.add(randomLongUrl());
        }
    }

    @Test
    @Order(1)
    void create_expect_incrementId_when_create_short_url_one_by_one() {
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
    void query_long_url_expect_long_url_when_query_by_short_url() {
        for (int i = 0; i < longUrls.size(); i++) {
            ResponseEntity<LongShortMapModel> resp = service.queryLongUrl(String.valueOf(i));
            Assertions.assertNotNull(resp.getBody());
            Assertions.assertEquals(longUrls.get(i), resp.getBody().getLongUrl());
        }
    }

    @Test
    @Order(3)
    void create_expect_503_when_create_exceed_the_limit() {
        int remainingCapacity = MAX_MAP_LIMIT - longUrls.size();
        for (int i = 0; i < remainingCapacity; i++) {
            LongShortMapModel req = new LongShortMapModel();
            String longUrl = randomLongUrl();
            req.longUrl(longUrl);
            service.create(req);
            longUrls.add(longUrl);
        }
        LongShortMapModel req = new LongShortMapModel();
        req.longUrl(randomLongUrl());
        ResponseEntity<LongShortMapModel> resp = service.create(req);
        Assertions.assertNotNull(resp);
        Assertions.assertEquals(HttpStatus.INSUFFICIENT_STORAGE.value(), resp.getStatusCode());
    }

    @Test
    @Order(4)
    void create_expect_the_same_short_url_when_repeat_creat() {
        int longUrlIndex = RandomUtils.nextInt(0, longUrls.size());
        String longUrl = longUrls.get(longUrlIndex);
        LongShortMapModel req = new LongShortMapModel();
        req.longUrl(longUrl);
        ResponseEntity<LongShortMapModel> resp = service.create(req);
        Assertions.assertNotNull(resp.getBody());
        Assertions.assertEquals(String.valueOf(longUrlIndex), resp.getBody().getShortUrl());
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
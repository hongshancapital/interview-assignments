package com.homework.shorturl.service;

import com.homework.shorturl.TestUtil;
import com.homework.shorturl.model.LongShortMapModel;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ShortUrlServiceTest {
    @Autowired
    private ShortUrlService service;

    @Value("${shorturl.maxSupportCapacity}")
    private int maxSupportCapacity;

    private static final int INITIAL_CAPACITY = 10;
    private static final ArrayList<String> longUrls = new ArrayList<>(INITIAL_CAPACITY);


    @BeforeAll
    static void initLongUrls() {
        for (int i = 0; i < INITIAL_CAPACITY; i++) {
            longUrls.add(TestUtil.randomLongUrl());
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
        int remainingCapacity = maxSupportCapacity - longUrls.size();
        for (int i = 0; i < remainingCapacity; i++) {
            LongShortMapModel req = new LongShortMapModel();
            String longUrl = TestUtil.randomLongUrl();
            req.longUrl(longUrl);
            service.create(req);
            longUrls.add(longUrl);
        }
        LongShortMapModel req = new LongShortMapModel();
        req.longUrl(TestUtil.randomLongUrl());
        ResponseEntity<LongShortMapModel> resp = service.create(req);
        Assertions.assertNotNull(resp);
        Assertions.assertEquals(HttpStatus.INSUFFICIENT_STORAGE.value(), resp.getStatusCode().value());
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

    @Test
    @Order(5)
    void query_expect_short_url_not_found_when_never_create(){
        ResponseEntity<LongShortMapModel> resp = service.queryLongUrl(String.valueOf(10_000));
        Assertions.assertNotNull(resp);
        Assertions.assertEquals(HttpStatus.NOT_FOUND,resp.getStatusCode());
    }
}
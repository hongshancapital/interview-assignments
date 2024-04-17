package com.sequoia.service;

import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutionException;

/**
 * Descript:
 * File: com.sequoia.service.TinyUrlServiceTest
 * Author: daishengkai
 * Date: 2022/3/31 02:38
 * Copyright (c) 2022,All Rights Reserved.
 */
@Slf4j
@SpringBootTest
public class TinyUrlServiceTest {

    @Autowired
    private ITinyUrlService tinyUrlService;

    @Test
    public void testgetTinyUrlFuture() {
        for (String originUrl : Sets.newHashSet(null, "null", "test.com", "test.cn/fsggssg11")) {
            try {
                String tinyCode = tinyUrlService.getTinyUrlFuture(originUrl).get();
                log.info("{}  -  {}", originUrl, tinyCode);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testgetTinyUrlFutureSame() throws ExecutionException, InterruptedException {
        String originUrl = "test.com";
        String tinyCode1 = tinyUrlService.getTinyUrlFuture(originUrl).get();

        String tinyCode2 = tinyUrlService.getTinyUrlFuture(originUrl).get();
        log.info("originUrl:{} -> {}  -  {}", originUrl, tinyCode1, tinyCode2);

        Assertions.assertEquals(tinyCode1, tinyCode2);
    }

    @Test
    public void testgetOriginUrlFuture() throws ExecutionException, InterruptedException {
        String originUrl = tinyUrlService.getOriginUrl(null);
//        Assertions.assertEquals(null, originUrl);

        originUrl = tinyUrlService.getOriginUrl("null");
        Assertions.assertEquals(null, originUrl);
    }

}

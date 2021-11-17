package com.icbc.gjljfl.service;

import com.icbc.gjljfl.area.service.impl.AreaServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AreaServiceTest {
    private static AreaServiceImpl areaServiceImpl = new AreaServiceImpl();
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void testSaveUrl() {
        String url = "www.com";
        areaServiceImpl.saveUrl( url);
        assertEquals(areaServiceImpl.saveUrl( url),"short_url1");
        System.out.println("   testSaveUrl()-->url_short: "+areaServiceImpl.saveUrl( url));
    }

    @Test
    void testReadUrl() {
        String url = "short_url1";
        areaServiceImpl.readUrl( url);
        assertEquals(areaServiceImpl.readUrl( url),"www.com");
        System.out.println("   testReadUrl()-->url_long: "+areaServiceImpl.readUrl( url));
    }

    @Test
    void testCompressNumber() {
        long number = 1000L;
        areaServiceImpl.compressNumber( number);
        assertEquals(areaServiceImpl.compressNumber( number),"www.com");
    }

}
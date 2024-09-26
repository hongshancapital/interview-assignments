package org.demo.shortlink.service.impl;

import org.demo.shortlink.service.CacheService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wsq
 * @date 2022/3/26 002618:38
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class CacheServiceImplTest {

    @Resource
    CacheService cacheService;

    @Test
    void put() {
        cacheService.put("https://www.msn.cn/zh-cn/health/medical/%E5%BE%B7%E5%9B%BD%E6%97%A5%E5%A2%9E%E6%96%B0%E5%86%A0%E7%A1%AE%E8%AF%8A%E7%97%85%E4%BE%8B%E6%95%B0%E9%A6%96%E8%B6%8530%E4%B8%87%E4%BE%8B/ar-AAVsO8f?ocid=msedgntp&cvid=c133fa8dd5284f62a10431cdb47c2b0e", "sdfb333");
    }

    @Test
    void get() {
        cacheService.get("sdfb333");
        cacheService.get(null);
    }
}
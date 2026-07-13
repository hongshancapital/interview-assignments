package com.alexyuan.shortlink.service;

import com.alexyuan.shortlink.common.variant.CacheVariant;
import com.alexyuan.shortlink.config.CaffeineCacheConfig;
import com.alexyuan.shortlink.service.impl.CaffeineCacheServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CaffeineCacheServiceTest {

    @Resource
    CaffeineCacheConfig caffeineCacheConfig;

    @Resource
    CaffeineCacheServiceImpl caffeineCacheService;

    @Test
    public void testCacheNormalCase() {
        String cur_time = String.valueOf(System.currentTimeMillis());
        CacheVariant cacheVariant = new CacheVariant("000001", "000001"
                , "http://alexkxyuan.test", cur_time);
        assertThat(caffeineCacheService.cachePut("test", cacheVariant)).isEqualTo(cacheVariant);
        assertThat(caffeineCacheService.cacheGet("test")).isEqualTo(cacheVariant);
    }

    @Test
    public void testCacheCornerCase1() {
        assertThat(caffeineCacheService.cachePut("test", null)).isEqualTo(null);
        assertThat(caffeineCacheService.cacheGet(null)).isEqualTo(null);
        assertThat(caffeineCacheService.cacheGet("notExist")).isEqualTo(null);
    }
}

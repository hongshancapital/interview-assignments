package com.icbc.gjljfl.service;

import com.icbc.gjljfl.area.service.impl.AreaServiceImpl;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class AreaServiceTest {
    private static AreaServiceImpl areaServiceImpl = new AreaServiceImpl();
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    private static final  String KeyPrefix = "short_url_hash_md5_key_";
    private static final  String shortUrlKeyPrefix = "short_url_hash_key_";
    private static final  String serialNumberKey = "short_url_serial_number";
    private String shortUrlPrefix = "short_url";
    private Long shortUrlTimeout = 1000L;
    private static final String SHORT_URL_PREFIX = "/t/";
    private static final  String SHORT_URL_KEY_PREFIX = "short_url_hash_key_";

    @Test
    public void testSaveUrl() {
        String url_short = "www.baidu.com";
        assertNull(areaServiceImpl.saveUrl(""));
        assertNull(areaServiceImpl.saveUrl(null));
        assertNotNull(areaServiceImpl.saveUrl(url_short));
        assertTrue(url_short.length()<=8 && null != url_short, url_short);
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        assertNotNull(operations);
        String md5 = DigestUtils.md5Hex(url_short);
        assertNotNull(md5);
        String shortUrl = operations.get(KeyPrefix + md5);
        assertNotNull(shortUrl);
        Long value = stringRedisTemplate.opsForValue().increment(serialNumberKey, 1);
        String longString = areaServiceImpl.compressNumber(value);
        assertNotNull(longString);
        shortUrl = shortUrlPrefix + longString;
        assertNotNull(shortUrl);
        assertEquals(areaServiceImpl.saveUrl( url_short),"short_url1");
        assertNotEquals(areaServiceImpl.saveUrl( url_short),url_short);
    }

    @Test
    public void testReadUrl() {
        String url_long = "long_url1";
        assertNull(areaServiceImpl.readUrl(""));
        assertNull(areaServiceImpl.readUrl(null));
        assertNotNull(areaServiceImpl.readUrl(url_long));
        assertNotNull(url_long);
        assertThat(url_long,url_long.length()<=8);
        assertTrue(url_long.length()<=8 && null != url_long, url_long);
        String longUrl = stringRedisTemplate.opsForValue().get(SHORT_URL_KEY_PREFIX + url_long.substring(url_long.length() - 1));
        assertNotNull(longUrl);
        Long value = stringRedisTemplate.opsForValue().increment(serialNumberKey, 1);
        String longString = areaServiceImpl.compressNumber(value);
        assertNotNull(longString);
        assertEquals(areaServiceImpl.readUrl( url_long),"www.baidu.com");
        assertNotEquals(areaServiceImpl.readUrl( url_long),url_long);
    }

    @Test
    void testCompressNumber() {
        long number = 1000L;
        areaServiceImpl.compressNumber( number);
        assertEquals(areaServiceImpl.compressNumber( number),"www.com");
    }

}

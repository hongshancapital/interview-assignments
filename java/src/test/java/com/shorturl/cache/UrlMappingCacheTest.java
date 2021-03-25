package com.shorturl.cache;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * Created by ruohanpan on 21/3/23.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlMappingCacheTest {

    @Autowired
    private Cache cache;

    @Autowired
    private StringRedisTemplate template;

    private Long code = 2048L;

    private String url = "http://www.titizz.com";

    @Before
    public void prepareData() {
        template.opsForValue().set(code.toString(), url, 1, TimeUnit.HOURS);
        template.opsForValue().set(url, code.toString(), 1, TimeUnit.HOURS);
    }

    @Test
    public void put() throws Exception {
        String code = "1024";
        String url = "http://www.baidu.com";
        cache.put(code, url);

        assertEquals(code, template.opsForValue().get(url));
        assertEquals(url, template.opsForValue().get(code));
    }

    @Test
    public void getCode() throws Exception {
        assertEquals(code, cache.getCode(url));
    }

    @Test
    public void getUrl() throws Exception {
        assertEquals(url, cache.getUrl(code.toString()));
    }
}
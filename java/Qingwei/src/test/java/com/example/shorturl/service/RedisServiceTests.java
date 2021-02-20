package com.example.shorturl.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * RedisService Tester.
 *
 * @author <mencius>
 * @version 1.0
 * @since <pre>2�� 19, 2021</pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RedisServiceTests {

    private static Logger logger = LoggerFactory.getLogger(RedisServiceTests.class);
    @Autowired(required = false)
    private RedisService redisService;

    /**
     * Method: get(final String key)
     */
    @Test
    public void testGet() throws Exception {
        final String val = redisService.get("key");
        logger.info(val);
    }

    /**
     * Method: set(final String key, String value)
     */
    @Test
    public void testSetForKeyValue() throws Exception {
        final boolean set = redisService.set("key", "value");
    }

    /**
     * Method: set(final String key, String value, Integer expireTime)
     */
    @Test
    public void testSetForKeyValueExpireTime() throws Exception {
        final boolean set = redisService.set("key", "value", 10);
    }

}

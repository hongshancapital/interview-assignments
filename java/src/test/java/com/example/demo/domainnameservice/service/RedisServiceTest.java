package com.example.demo.domainnameservice.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Redis Service Test.
 *
 * @author laurent
 * @date 2021-12-11 下午4:42
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RedisServiceTest {

    private static final String key = "www.xxx.com";

    private static final String value = "ASKLD09I";

    @Autowired
    private RedisService redisService;

    @Test
    public void setAndGetTest() {
        redisService.setValue(key, value, 100);
        Assert.assertEquals(value, redisService.getValue(key));
    }

}
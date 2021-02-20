package com.example.surl.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("redis工具")
class RedisUtilTest {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    @DisplayName("redis工具增删改查测试")
    void get() {
        String str = "test";
        String key = "test:redis:util";
        boolean set = redisUtil.set(key, str);
        Assertions.assertTrue(set);
        String getStr = redisUtil.get(key);
        Assertions.assertEquals(str, getStr);
        String modifyStr = "test2";
        redisUtil.getAndSet(key, modifyStr);
        getStr = redisUtil.get(key);
        Assertions.assertEquals(modifyStr, getStr);
        boolean delete = redisUtil.delete(key);
        Assertions.assertTrue(delete);
    }

}
package com.xiaoxi666.tinyurl.service.store;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @Author: xiaoxi666
 * @Date: 2022/4/21
 * @Version: 1.0
 * @Description: 缓存测试
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
class AbstractStoreTest {

    @Autowired
    PermanentStore permanentStore;

    @Test
    void putAndGetTest() {
        String tinyPath = "tinyPath";
        String longUrl = "longUrl";
        permanentStore.put(tinyPath, longUrl);
        Assertions.assertEquals(longUrl, permanentStore.get(tinyPath));
    }
}
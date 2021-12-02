package com.zhangliang.suril.util;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

import javax.annotation.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MemoryDataStoreTest {

    @Value("${config.max-capacity}")
    private Integer maxCapacity;
    @Resource
    private MemoryDataStore memoryDataStore;

    @BeforeEach
    void setUp() {
    }

    @Test
    void set() {
        for (int i = 0; i <= maxCapacity; i++) {
            memoryDataStore.set("http://123.com/" + i, "http://123.com/" + i);
        }
//        Mockito.when(memoryDataStore.set(anyString(), anyString())).thenThrow(new IllegalArgumentException());

        try {
            memoryDataStore.set("http://123.com/","http://123.com/");
            Assert.isTrue(false,"走到这里就错了");
        } catch (Exception ex){
            Assert.isTrue(ex instanceof IllegalArgumentException,"必须是 IllegalArgumentException");
        }
    }
}

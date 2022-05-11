package com.wangxiao.shortlink.resource;

import com.wangxiao.shortlink.infrastructure.common.StoreOverFlowException;
import com.wangxiao.shortlink.infrastructure.persisitence.Persistence;
import com.wangxiao.shortlink.infrastructure.properties.ShortLinkProperties;
import org.assertj.core.util.Files;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
class ShortLinkResourceExecptionTest {
    @Resource
    ShortLinkResource shortLinkResource;

    @Resource
    private ShortLinkProperties shortLinkProperties;

    @Resource
    private Persistence persistenceService;

    @AfterEach
    void cleanStore() {
        Files.delete(new File(shortLinkProperties.getStorePath()));
    }

    @Test
    void testStoreLimit() {
        shortLinkResource.encode("https://www.baidu.com/1").getData();
        shortLinkResource.encode("https://www.baidu.com/2").getData();
        shortLinkResource.encode("https://www.baidu.com/3").getData();
        Assertions.assertThrows(StoreOverFlowException.class, () -> shortLinkResource.encode("https://www.baidu.com/4").getData());
    }

    @Test
    void testPersist() throws IOException {
        Assertions.assertDoesNotThrow(() -> persistenceService.persist("01AEQ0vU", "https://www.baidu.com/abc"));
    }
}


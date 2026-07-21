package com.yilong.shorturl.config;

import com.yilong.shorturl.storage.UrlStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class UrlStorageConfigTest {

    @Test
    public void testUrlStorage() {
        UrlStorageConfig urlStorageConfig = new UrlStorageConfig();
        urlStorageConfig.setMaximumSize(10000);
        Assertions.assertEquals(10000, urlStorageConfig.getMaximumSize());
        UrlStorage storage = urlStorageConfig.urlStorage();
    }
}

package com.polly.shorturl.cache;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author polly
 * @date 2022.03.20 10:59:32
 */
@SpringBootTest
public class UrlCacheTestCase {

    @Autowired
    private UrlCache cache;

    @Test
    public void clean() {
        cache.memoryClean();
    }
}
package com.shorts.url.service.generator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class GeneratorTests {

    @Resource
    private Generator generator;

    @Test
    void testGenerate() {
        Assertions.assertNotNull(generator.generate("https://www.baidu.com"));
    }

    @Test
    void testGet() {
        String longUrl = "https://www.baidu.com";
        String shortUrl = generator.generate(longUrl);
        Assertions.assertEquals(longUrl, generator.get(shortUrl));
    }
}

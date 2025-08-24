package com.sequoia.shorturl.common.server;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ShortUrlGeneratorTest {

    @Test
    public void longUrlToShortUrl() {
        String baidu = ShortUrlGenerator.generate("www.baidu.com");
        assertEquals("4ZYBFt", baidu);

        String google = ShortUrlGenerator.generate("www.google.com");
        assertEquals("37UQtk", google);
    }


}
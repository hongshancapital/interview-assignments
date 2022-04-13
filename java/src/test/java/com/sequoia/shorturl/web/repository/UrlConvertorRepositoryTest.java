package com.sequoia.shorturl.web.repository;

import com.sequoia.shorturl.common.server.ShortUrlGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UrlConvertorRepositoryTest {
    @Autowired
    private UrlConvertorRepository urlConvertorRepository;

    private static final String testUrl = "https://www.sina.com.cn";

    @Test
    public void getLongUrlByShortUrl() {
        String shortUrl = ShortUrlGenerator.generate(testUrl);
        urlConvertorRepository.save(shortUrl, testUrl);

        String longUrl = urlConvertorRepository.getLongUrlByShortUrl(shortUrl);
        assertEquals(testUrl, longUrl);
    }


}
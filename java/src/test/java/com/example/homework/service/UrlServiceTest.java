package com.example.homework.service;

import com.example.homework.HomeworkApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest(classes = HomeworkApplication.class)
class UrlServiceTest {

    @Autowired
    private UrlService urlService;

    @Test
    void getLongUrl() {
        String shortUrl1 = saveLongUrl();
        String url1 = urlService.getLongUrl(shortUrl1);
        log.info(url1);
        String shortUrl2 = saveLongUrl();
        assert shortUrl1.equals(shortUrl2);
        String url2 = urlService.getLongUrl("99999");
        assert url2 == null;
    }

    @Test
    String saveLongUrl() {
        String longUrl = "https://github.com/scdt-china/interview-assignments";
        String shortUrl = urlService.saveLongUrl(longUrl);
        assert shortUrl != null;
        return shortUrl;
    }
}
package com.polly.shorturl.config;

import com.polly.shorturl.service.IShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author polly
 * @date 2022.03.26 20:01:22
 */
@Component
public class InitUrl implements CommandLineRunner {
    @Autowired
    private IShortUrlService service;

    @Override
    public void run(String... args) throws Exception {
        service.insertShortUrl("test");
    }
}

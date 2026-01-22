package com.scdt.url.tiny_url;

import com.scdt.url.BaseComponentTest;
import com.scdt.url.common.util.ShortStringGenerator;
import com.scdt.url.tiny_url.model.TinyUrl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.scdt.url.tiny_url.model.TinyUrlId.tinyUrlId;
import static org.junit.jupiter.api.Assertions.assertEquals;


class TinyUrlRepositoryComponentTest extends BaseComponentTest {

    @Autowired
    private TinyUrlRepository tinyRepository;

    @Test
    void testSaveTinyUrl() {

        var originalUrl = "http://asdsa.test.com/qweqeqweqew";
        String shortUrl = ShortStringGenerator.generate(originalUrl, 8);
        TinyUrl tinyUrl = TinyUrl.create(tinyUrlId(shortUrl), originalUrl);
        tinyRepository.save(tinyUrl);

        TinyUrl saved = tinyRepository.byId(tinyUrl.getId());
        assertEquals(tinyUrl.getId(), saved.getId());
        assertEquals(tinyUrl.getCreatedAt(), saved.getCreatedAt());
        assertEquals(tinyUrl.getOriginalUrl(), saved.getOriginalUrl());
        assertEquals(tinyUrl, saved);
    }

}
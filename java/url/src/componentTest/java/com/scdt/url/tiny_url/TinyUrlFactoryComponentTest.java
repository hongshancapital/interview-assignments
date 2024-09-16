package com.scdt.url.tiny_url;

import com.scdt.url.BaseComponentTest;
import com.scdt.url.tiny_url.model.TinyUrl;
import com.scdt.url.tiny_url.model.TinyUrlFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;


class TinyUrlFactoryComponentTest extends BaseComponentTest {

    @Autowired
    private TinyUrlFactory tinyUrlFactory;

    @Test
    void testCreateTinyUrl() {
        var originalUrl = "http://asdsa.test.com/asdadsadsasd";
        TinyUrl tinyUrl = tinyUrlFactory.create(originalUrl);
        assertEquals(originalUrl, tinyUrl.getOriginalUrl());
    }
}
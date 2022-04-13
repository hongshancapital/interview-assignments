package com.tinyurl.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TinyUrlCodecServiceImplTest {

    private String url;
    private TinyUrlCodecServiceImpl tinyUrlCodecServiceImpl;

    @BeforeEach
    void setUp() {
        url = "http://www.test.com/encode.html";
        tinyUrlCodecServiceImpl = new TinyUrlCodecServiceImpl();
    }

    @Test
    void encode() {
        String encodeUrl = tinyUrlCodecServiceImpl.encode(url);
        assertEquals(27, encodeUrl.length());
    }


    @Test
    void decode() {
        String encodeUrl = tinyUrlCodecServiceImpl.encode(url);
        assertEquals(url, tinyUrlCodecServiceImpl.decode(encodeUrl));
    }
}
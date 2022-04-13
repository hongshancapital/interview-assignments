package com.tinyurl.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RandomTinyUrlCodecTest {

    private String url;
    private RandomTinyUrlCodec randomTinyUrlCodec;

    @BeforeEach
    void setUp() {
        url = "http://www.test.com/encode.html";
        randomTinyUrlCodec = new RandomTinyUrlCodec();
    }

    @Test
    void encode() {
        String encodeUrl = randomTinyUrlCodec.encode(url);
        assertEquals(27, encodeUrl.length());
    }

    @Test
    void encodeNull() {
        String encodeUrl = randomTinyUrlCodec.encode("");
        assertNull(encodeUrl);
    }

    @Test
    void encodeSameUrl() {
        String encodeUrl = randomTinyUrlCodec.encode(url);
        assertEquals(27, encodeUrl.length());

        String encodeDuplicatedUrl = randomTinyUrlCodec.encode(url);
        assertEquals(encodeUrl, encodeDuplicatedUrl);
    }


    @Test
    void decode() {
        String originalUrl = randomTinyUrlCodec.encode(url);
        assertEquals(url, randomTinyUrlCodec.decode(originalUrl));
    }

    @Test
    void decodeNull() {
        String originalUrl = randomTinyUrlCodec.decode("");
        assertNull(originalUrl);
    }
}
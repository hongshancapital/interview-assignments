package com.mortimer.shortenurl.vo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UrlMappingTest {
    @Test
    public void test_constructor() {
        String shortUrl = "aaa";
        String longUrl = "bbb";
        UrlMapping urlMapping = new UrlMapping(shortUrl, longUrl);
        Assertions.assertEquals(shortUrl, urlMapping.getShortUrl());
        Assertions.assertEquals(longUrl, urlMapping.getLongUrl());
    }
}

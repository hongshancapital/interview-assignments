package com.domain.urlshortener.vo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author: rocky.hu
 * @date: 2022/4/6 3:21
 */
public class ShortUrlGetRequestTest {

    @Test
    public void test_constructor() {
        new ShortUrlGetRequest();
    }

    @Test
    public void test_setLongUrl() {
        ShortUrlGetRequest ShortUrlGetRequest = new ShortUrlGetRequest();
        ShortUrlGetRequest.setUrl("A");
        Assertions.assertEquals("A", ShortUrlGetRequest.getUrl());
    }

    @Test
    public void test_toString() {
        new ShortUrlGetRequest().toString();
    }

    @Test
    public void test_hashCode() {
        new ShortUrlGetRequest().hashCode();
    }

    @Test
    public void test_equals() {
        ShortUrlGetRequest a = new ShortUrlGetRequest();
        ShortUrlGetRequest b = new ShortUrlGetRequest();
        Assertions.assertFalse(a.equals(b));
    }
    
}

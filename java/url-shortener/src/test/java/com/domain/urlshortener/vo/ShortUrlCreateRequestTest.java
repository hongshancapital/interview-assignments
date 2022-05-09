package com.domain.urlshortener.vo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author: rocky.hu
 * @date: 2022/4/6 3:21
 */
public class ShortUrlCreateRequestTest {

    @Test
    public void test_constructor() {
        new ShortUrlCreateRequest();
    }

    @Test
    public void test_setLongUrl() {
        ShortUrlCreateRequest shortUrlCreateRequest = new ShortUrlCreateRequest();
        shortUrlCreateRequest.setUrl("A");
        Assertions.assertEquals("A", shortUrlCreateRequest.getUrl());
    }

    @Test
    public void test_toString() {
        new ShortUrlCreateRequest().toString();
    }

    @Test
    public void test_hashCode() {
        new ShortUrlCreateRequest().hashCode();
    }

    @Test
    public void test_equals() {
        ShortUrlCreateRequest a = new ShortUrlCreateRequest();
        ShortUrlCreateRequest b = new ShortUrlCreateRequest();
        Assertions.assertFalse(a.equals(b));
    }

}

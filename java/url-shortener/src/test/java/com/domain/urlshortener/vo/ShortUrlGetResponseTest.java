package com.domain.urlshortener.vo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author: rocky.hu
 * @date: 2022/4/6 3:21
 */
public class ShortUrlGetResponseTest {

    @Test
    public void test_constructor() {
        ShortUrlGetResponse ShortUrlGetResponse = new ShortUrlGetResponse("a", "b");
        Assertions.assertEquals("a", ShortUrlGetResponse.getLongUrl());
        Assertions.assertEquals("b", ShortUrlGetResponse.getShortUrl());
    }
    
}

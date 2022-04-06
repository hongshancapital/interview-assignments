package com.domain.urlshortener.vo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author: rocky.hu
 * @date: 2022/4/6 3:21
 */
public class ShortUrlCreateResponseTest {

    @Test
    public void test_constructor() {
        ShortUrlCreateResponse shortUrlCreateResponse = new ShortUrlCreateResponse("a", "b");
        Assertions.assertEquals("a", shortUrlCreateResponse.getLongUrl());
        Assertions.assertEquals("b", shortUrlCreateResponse.getShortUrl());
    }

    
}

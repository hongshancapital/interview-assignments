package com.example.pengchao.shorturl.utils;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author pengchao04
 * @date 2022/5/23 11:42
 */
public class ShortUrlUtilsTest {

    @Test
    void generateShortenUrl() {
        // url id从1开始，因此得到的短url也是1
        String shortUrl = ShortUrlUtils.generateShortenUrl("http://www.baidu.com");
        MatcherAssert.assertThat(shortUrl.equals("1"), Matchers.is(true));
    }

    @Test
    public void to62RadixString() {
        long id = 100;
        String radixString = ShortUrlUtils.to62RadixString(id);
        MatcherAssert.assertThat(radixString.equals("C1"), Matchers.is(true));
    }
}
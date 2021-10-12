package com.moonciki.interview.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

@Slf4j
public class ShortUrlGeneratorTest {

    @Test
    public void testShortUrl() {

        String fullUrl = "https://www.sojson.com/encrypt_md5.html";

        String[] shortArray = ShortUrlGenerator.shortUrl(fullUrl);

        String fullTmp = StringUtils.join(shortArray, "-");

        System.out.println(fullTmp);

    }

    public static void main(String[] args) {

        System.out.println("aaa");

    }

}
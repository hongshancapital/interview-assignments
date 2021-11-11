package com.lynnhom.sctdurlshortservice.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @description: UrlShortenUtil测试类
 * @author: Lynnhom
 * @create: 2021-11-10 11:32
 **/
public class UrlShortenUtilTest {

    @Test
    void testGenerate() {
        Assertions.assertEquals("hAEu9V",
                UrlShortenUtil.generate("https://github.com/scdt-china/interview-assignments/tree/master/java"));
        Assertions.assertEquals("hjgqWi",
                UrlShortenUtil.generate("https://github.com/scdt-china/interview-assignments/tree/master/design"));
        Assertions.assertEquals("hxU8n2",
                UrlShortenUtil.generate("https://github.com/scdt-china/interview-assignments/tree/master/dev-ops"));
        Assertions.assertEquals("eNOZWY",
                UrlShortenUtil.generate("https://github.com/scdt-china/interview-assignments/tree/master/frontend"));
        Assertions.assertEquals("e3WuDO",
                UrlShortenUtil.generate("https://github.com/scdt-china/interview-assignments/tree/master/fullstack"));
        Assertions.assertEquals("eYvlAF",
                UrlShortenUtil.generate("https://github.com/scdt-china/interview-assignments/tree/master/php"));
        Assertions.assertEquals("eZEUKF",
                UrlShortenUtil.generate("https://github.com/scdt-china/interview-assignments/tree/master/qa"));
        Assertions.assertEquals("90DUMh",
                UrlShortenUtil.generate("https://github.com/scdt-china/interview-assignments/tree/master/swift"));
        Assertions.assertEquals("hpoZMZ",
                UrlShortenUtil.generate("https://github.com/scdt-china/interview-assignments"));
        Assertions.assertEquals("9Yebc8",
                UrlShortenUtil.generate("https://github.com/"));
        Assertions.assertEquals(6,
                UrlShortenUtil.generate("https://github.com/scdt-china/interview-assignments/tree/master/java").length());

    }
}

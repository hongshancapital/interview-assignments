package com.zmc.shorturl.utils;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HttpUtilsTest {

    @Test
    public void testHttpVerify() {
        String http = "http://www.baidu.com/111";
        boolean ret = HttpUtils.verifyUrl(http);
        assertTrue(ret);
    }

    @Test
    public void testHttpsVerify() {
        String http = "https://www.baidu.com/111";
        boolean ret = HttpUtils.verifyUrl(http);
        assertTrue(ret);
    }

    @Test
    public void testHttpsVerify2() {
        String http = "http2://www.baidu.com/111";
        boolean ret = HttpUtils.verifyUrl(http);
        assertTrue(!ret);
    }
}
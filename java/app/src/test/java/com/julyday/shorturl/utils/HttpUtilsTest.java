package com.julyday.shorturl.utils;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class HttpUtilsTest {
    @Test
    public void testVerifyUrl1() {
        String longUrl = "http://www.julyday.com/test/01";
        assertTrue(HttpUtils.verifyUrl(longUrl));
    }

    @Test
    public void testVerifyUrl2() {
        String longUrl = "https://www.julyday.com/test/01";
        assertTrue(HttpUtils.verifyUrl(longUrl));
    }

    @Test
    public void testVerifyUrl3() {
        String longUrl = "www.julyday.com/test/01";
        assertTrue(!HttpUtils.verifyUrl(longUrl));
    }

    @Test
    public void testVerifyUrl4() {
        String longUrl = "";
        assertTrue(!HttpUtils.verifyUrl(longUrl));
    }

    @Test
    public void testVerifyUrl5() {
        HttpUtils httpUtils = new HttpUtils();
        String longUrl = "";
        assertTrue(!httpUtils.verifyUrl(longUrl));
    }
}

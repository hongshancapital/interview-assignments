package com.sequoia.domain.util;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UrlCheckerTest {

    @Test
    public void testUrlInValid() {
        String[] urls = new String[]{
                "sftp://tmp", "http://../test", "wss:/www.baidu.com", "ws:/www.baidu.com", ""
        };
        for (String url : urls) {
            assertThrows(IllegalArgumentException.class, () -> UrlChecker.check(Base64.getEncoder().encodeToString(url.getBytes(StandardCharsets.UTF_8))));
            //check unbase64
            assertThrows(IllegalArgumentException.class, () -> UrlChecker.check(url));
        }
    }

    @Test
    public void testUrlValid() {
        String[] urls = new String[]{
                "http://www.baidu.com?param=123", "https://www.baidu.com?param=123", "https://www.baidu.com?param=123&param2=123"
        };

        for (String url : urls) {
            assertDoesNotThrow(() -> UrlChecker.check(Base64.getEncoder().encodeToString(url.getBytes(StandardCharsets.UTF_8))));
            //check unbase64
            assertThrows(IllegalArgumentException.class, () -> UrlChecker.check(url));
        }
    }
}
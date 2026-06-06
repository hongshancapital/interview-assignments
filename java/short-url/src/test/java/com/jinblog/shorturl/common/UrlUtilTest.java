package com.jinblog.shorturl.common;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;

// 指定profile
@Profile("default")
@SpringBootTest
// 使用spring的测试框架
@ExtendWith(SpringExtension.class)
class UrlUtilTest {

    @Test
    void validateLongUrl() {
        assertFalse(UrlUtil.validateLongUrl("aaa"));
        assertTrue(UrlUtil.validateLongUrl("http://a.com"));
        assertTrue(UrlUtil.validateLongUrl("https://a.com"));
        assertFalse(UrlUtil.validateLongUrl("https://a."));
        assertFalse(UrlUtil.validateLongUrl("htts://a."));
    }

    @Test
    void usedMemoryPercent() {
        UrlUtil.usedMemoryPercent();
    }
}
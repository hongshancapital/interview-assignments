package com.sxg.shortUrl.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.sxg.shortUrl.utils.UrlUtil;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("url工具类")
class UrlUtilTest {
    @Test
    @DisplayName("url验证")
    void isValidUrl() {
        String s = "htt://qq.com";
        String s1 = "http:qq.com";
        String s2 = "http:/qq.com";
        String str = "http://qq.com";
        String str1 = "http://qq.com/path";
        String str2 = "http://qq.com/path?val=1";
        String str3 = "http://qq.com/path?val=1&val2=2#location";
        Assertions.assertFalse(UrlUtil.isValidUrl(s));
        Assertions.assertFalse(UrlUtil.isValidUrl(s1));
        Assertions.assertFalse(UrlUtil.isValidUrl(s2));
        Assertions.assertTrue(UrlUtil.isValidUrl(str));
        Assertions.assertTrue(UrlUtil.isValidUrl(str1));
        Assertions.assertTrue(UrlUtil.isValidUrl(str2));
        Assertions.assertTrue(UrlUtil.isValidUrl(str3));
    }

}
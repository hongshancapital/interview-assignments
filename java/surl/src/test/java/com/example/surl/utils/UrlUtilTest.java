package com.example.surl.utils;

import cn.hutool.core.util.StrUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("url工具类")
class UrlUtilTest {

    @Test
    @DisplayName("Long类型转62进制码")
    void convertToStr() {
        long l = SequenceUtil.nextId();
        String s = UrlUtil.convertToStr(l);
        Assertions.assertEquals(8, StrUtil.length(s));
    }


    @Test
    @DisplayName("url验证")
    void isValidUrl() {
        String s = "htt://baidu.com";
        String s1 = "http:baidu.com";
        String s2 = "http:/baidu.com";
        String str = "http://baidu.com";
        String str1 = "http://baidu.com/path";
        String str2 = "http://baidu.com/path?val=1";
        String str3 = "http://baidu.com/path?val=1&val2=2#location";
        Assertions.assertFalse(UrlUtil.isValidUrl(s));
        Assertions.assertFalse(UrlUtil.isValidUrl(s1));
        Assertions.assertFalse(UrlUtil.isValidUrl(s2));
        Assertions.assertTrue(UrlUtil.isValidUrl(str));
        Assertions.assertTrue(UrlUtil.isValidUrl(str1));
        Assertions.assertTrue(UrlUtil.isValidUrl(str2));
        Assertions.assertTrue(UrlUtil.isValidUrl(str3));
    }

}
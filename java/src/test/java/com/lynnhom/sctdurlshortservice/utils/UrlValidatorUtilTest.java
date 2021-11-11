package com.lynnhom.sctdurlshortservice.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @description: UrlValidatorUtil测试类
 * @author: Lynnhom
 * @create: 2021-11-10 11:28
 **/
public class UrlValidatorUtilTest {

    @Test
    void testIsValid() {
        Assertions.assertEquals(Boolean.FALSE, UrlValidatorUtil.isValid("ftp:127.0.0.1"));
        Assertions.assertEquals(Boolean.FALSE, UrlValidatorUtil.isValid("http:/github.com/s"));
        Assertions.assertEquals(Boolean.TRUE, UrlValidatorUtil.isValid("http://github.com/s"));
    }
}

/**
 * @(#)UrlUtilsTest.java, 12月 27, 2021.
 * <p>
 * Copyright 2021 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.example.demo;

import com.example.demo.utils.UrlUtils;
import org.junit.*;

/**
 * @author zhengyin
 */
public class UrlUtilsTest {

    @Test
    public void testGetKey() {
        String shortUrl = "http://c.t.cn/asdfsdfsdfsdfsdf";
        String key = UrlUtils.getKey(shortUrl);
        Assert.assertEquals("asdfsdfsdfsdfsdf", key);
    }

    @Test
    public void testGenerateKey() {
        String longUrl = "http://1233rwfsdfs";
        String value = UrlUtils.generateKey(longUrl);
        Assert.assertEquals(value.length(), 6);
    }

}
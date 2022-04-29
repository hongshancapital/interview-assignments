package com.zp;

import com.zp.utils.UrlCheckUtil;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UrlCheckUtilTest {
    @Test
    public void urlCheckTest() {
        assertFalse(UrlCheckUtil.isValidUrl("dsfa"));
        assertFalse(UrlCheckUtil.isValidUrl("http"));
        assertTrue(UrlCheckUtil.isValidUrl("www.baidu.com"));
        assertTrue(UrlCheckUtil.isValidUrl("baidu.com"));
        assertTrue(UrlCheckUtil.isValidUrl("https://www.petrikainulainen.net/programming/maven/creating-code-coverage-reports-for-unit-and-integration-tests-with-the-jacoco-maven-plugin/"));
    }
}

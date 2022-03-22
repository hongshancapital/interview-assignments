package com.zc.shorturl.utils;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UrlUtilsTest {
    @Test
    public void testGetDomain() {
        assertThat(UrlUtils.getDomain("http://www.test.com/index"), equalTo("http://www.test.com"));
        assertThat(UrlUtils.getDomain(""), equalTo(""));
        assertThat(UrlUtils.getDomain("htt123"), equalTo(""));
        assertThat(UrlUtils.getDomain(null), equalTo(""));
    }
}

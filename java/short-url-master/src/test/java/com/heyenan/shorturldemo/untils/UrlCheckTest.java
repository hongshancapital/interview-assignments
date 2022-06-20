package com.heyenan.shorturldemo.untils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class UrlCheckTest {

    @Test
    @DisplayName("http格式校验正确")
    void checkURL() {
        boolean result = UrlCheck.checkURL("https://github.com/scdt-china/interview-assignments/tree/master/java");
        assertThat(result, equalTo(true));
    }

    @Test
    @DisplayName("http格式校验错误")
    void checkURL1() {
        boolean result = UrlCheck.checkURL("!https://github.com/scdt-china/interview-assignments/tree/master/java");
        assertThat(result, equalTo(false));
    }
}

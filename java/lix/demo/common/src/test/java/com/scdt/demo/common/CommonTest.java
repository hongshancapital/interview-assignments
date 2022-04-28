package com.scdt.demo.common;

import com.scdt.demo.common.web.CodeMessage;
import com.scdt.demo.common.web.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommonTest {

    @Test
    public void beanTest() {
        assertNotNull(CodeMessage.INVALID_URL.getCode());
        assertNotNull(CodeMessage.INVALID_URL.getMessage());

        Response<Integer> ok = Response.ok(111);
        assertNotNull(ok.getCode());
        Response<Object> fail = Response.fail(CodeMessage.INVALID_URL);
        assertNull(fail.getData());
    }

    @Test
    public void utilTest() {
        assertFalse(URLUtils.isValidUrl("http:."));
        assertTrue(URLUtils.isValidUrl("https://www.baidu.com/tieba"));
        assertFalse(URLUtils.isValidShortUrl("https://baidu.com"));
        assertTrue(URLUtils.isValidShortUrl("2fjj5a"));
        assertNotNull(URLUtils.generateShortUrl("https://kaito-kidds.com/2020/08/30/tfx-replication/"));
    }
}

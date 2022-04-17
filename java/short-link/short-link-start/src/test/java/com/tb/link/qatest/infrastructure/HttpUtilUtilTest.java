package com.tb.link.qatest.infrastructure;

import com.tb.link.infrastructure.util.HttpUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author andy.lhc
 * @date 2022/4/17 10:38
 */
public class HttpUtilUtilTest {

    @Test
    public void testHttp() {
        String url = "http://www.baidu.com?a=1&b=2";
        Assertions.assertTrue(HttpUtil.isHttp(url));

        url = "//www.baidu.com?a=1&b=2";
        Assertions.assertFalse(HttpUtil.isHttp(url));
        ;
    }

    @Test
    public void testHttps() {
        String url = "https://www.baidu.com?a=1&b=2";
        Assertions.assertTrue(HttpUtil.isHttps(url));

        url = "//www.baidu.com?a=1&b=2";
        Assertions.assertFalse(HttpUtil.isHttps(url));
        ;
    }

    @Test
    public void testHttpOrHttps() {
        String url = "https://www.baidu.com?a=1&b=2";
        Assertions.assertTrue(HttpUtil.checkHttpAndHttps(url));

        url = "//www.baidu.com?a=1&b=2";
        Assertions.assertFalse(HttpUtil.checkHttpAndHttps(url));
        ;
    }

}

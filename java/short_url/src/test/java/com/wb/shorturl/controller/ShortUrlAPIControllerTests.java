package com.wb.shorturl.controller;

import com.wb.shorturl.common.exception.BaseErrorCode;
import com.wb.shorturl.common.web.ApiResponse;
import com.wb.shorturl.tools.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author bing.wang
 * @date 2021/1/8
 */


@SpringBootTest
public class ShortUrlAPIControllerTests {

    @Autowired
    private ShortUrlAPIController shortUrlAPIController;


    /**
     * 测试合法长网址生成短网址
     *
     * @param url the url to be converted
     */

    @ParameterizedTest
    @ValueSource(strings = {"https://www.123.com", "http://www.abc.com", "https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&ch=&tn=baidu&bar=&wd=%E5%85%A8%E5%9B%BD%E7%96%AB%E6%83%85&oq=%E5%8C%97%E4%BA%AC%E7%96%AB%E6%83%85&rsv_pq=d4733834000065cb&rsv_t=f82ex8ASC3BvNJSI%2F%2BZx5DFtS6a2WeRUs7cP1k2DIdHN5x0x166RYkqw1pU&rqlang=cn"})
    void testGenerateShortUrlSuccess(String url) throws InterruptedException {
        ApiResponse res = shortUrlAPIController.generateShortUrl(url);
        Assertions.assertTrue(res.getStatus() == 200);
    }


    /**
     * 测试不合法长网址生成短网址
     *
     * @param url the url to be converted
     */

    @ParameterizedTest
    @ValueSource(strings = {"https:/www.123.com", "ftp://www.abc.com", "htt://www", ""})
    void testGenerateShortUrlFailure(String url) throws InterruptedException {
        ApiResponse res = shortUrlAPIController.generateShortUrl(url);
        Assertions.assertTrue(res.getStatus() == BaseErrorCode.PARAMS_ERROR.getCode());
    }
}

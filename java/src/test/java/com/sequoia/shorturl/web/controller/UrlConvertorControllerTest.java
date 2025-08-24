package com.sequoia.shorturl.web.controller;

import com.sequoia.shorturl.common.ApiResult;
import com.sequoia.shorturl.common.exception.ObjectNotExistException;
import com.sequoia.shorturl.web.service.IUrlConvertorService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class UrlConvertorControllerTest {

    @Autowired
    private UrlConvertorController urlConvertorController;

    private static final String testUrl = "https://www.sina.com.cn";

    @Test
    @Order(1)
    public void longUrlToShortUrl() {
        ApiResult<String> result = urlConvertorController.longUrlToShortUrl(testUrl);
        assertEquals("1pvSQD", result.getData());
    }

    @Test
    public void longUrlToShortUrlWithChinese() {
        ApiResult<String> result = urlConvertorController.longUrlToShortUrl(testUrl + "/abc?wish=新年快乐");
        assertEquals("14Fcc5", result.getData());
    }

    @Test
    public void longUrlToShortUrlWithEmpty() {
        ApiResult<String> result = urlConvertorController.longUrlToShortUrl("");
        assertEquals(400, result.getCode());
        assertEquals("", result.getData());
    }

    @Test
    @Order(2)
    public void getLongUrlByShortUrl() {
        urlConvertorController.longUrlToShortUrl(testUrl);
        ApiResult<String> result = urlConvertorController.getLongUrlByShortUrl("1pvSQD");
        assertEquals(testUrl, result.getData());

        try {
            ApiResult<String> result2 = urlConvertorController.getLongUrlByShortUrl("nnnnnn");
        } catch (ObjectNotExistException e) {

            assertEquals("请求的url不存在,请核对后再试!", e.getMessage());
        }
//        assertEquals(400, result2.getCode());
//        assertEquals("failure", result2.getMsg());
    }

    @Test
    public void getLongUrlByShortUrlWithNull() {
        IUrlConvertorService mock = Mockito.mock(IUrlConvertorService.class);

        UrlConvertorController urlConvertorController = new UrlConvertorController(mock);
        Mockito.when(mock.getLongUrlByShortUrl("123456")).thenReturn("");

        ApiResult<String> result = urlConvertorController.getLongUrlByShortUrl("123456");
        assertEquals("", result.getData());

    }

}
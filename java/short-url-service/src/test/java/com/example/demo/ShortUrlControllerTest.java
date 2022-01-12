package com.example.demo;


import com.example.demo.controller.ShortUrlController;
import com.example.demo.model.common.CommonResult;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class ShortUrlControllerTest {

    @Autowired
    private ShortUrlController shortUrlController;

    private static final String testUrl = "https://www.taobao.com.cn";

    @Test
    @Order(1)
    public void longUrlConvertShortUrl() {
        CommonResult<String> result = shortUrlController.longUrlConvertShortUrl(testUrl);
        assertEquals("CMBDvA", result.getData());
    }

    @Test
    public void longUrlConvertShortUrlWithChinese() {
        CommonResult<String> result = shortUrlController.longUrlConvertShortUrl(testUrl + "/abc?wish=新年快乐");
        assertEquals("rpG0F", result.getData());
    }

    @Test
    public void longUrlConvertShortUrlWithEmpty() {
        CommonResult<String> result = shortUrlController.longUrlConvertShortUrl("");
        assertEquals(400, result.getCode());
        assertEquals("", result.getData());
    }

    @Test
    @Order(2)
    public void shortUrlConvertLongUrl() {
        shortUrlController.longUrlConvertShortUrl(testUrl);
        CommonResult<String> result = shortUrlController.shortUrlConvertLongUrl("CMBDvA");
        assertEquals(testUrl, result.getData());

        try {
            CommonResult<String> result2 = shortUrlController.shortUrlConvertLongUrl("asdfdf");
        } catch (RuntimeException e) {

            assertEquals("请求的url不存在,请核对后再试!", e.getMessage());
        }
    }



}

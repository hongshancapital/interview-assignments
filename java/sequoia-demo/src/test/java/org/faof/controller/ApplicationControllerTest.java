package org.faof.controller;

import org.faof.domain.BaseRequest;
import org.faof.exceptions.BizException;
import org.faof.exceptions.ExceptionEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ApplicationControllerTest {

    @Autowired
    ApplicationController controller;

    @Test
    void health() {
        assertEquals(controller.health(), "ok!");
    }

    @Test
    void long2short() {
        BaseRequest request = new BaseRequest();
        assertThrows(BizException.class,
                () ->controller.long2short(request),
                ExceptionEnum.REQUEST_BODY_INVALID.getErrorMessage());
        request.setShortUrl("https://faof.cn/00000000");
        assertThrows(BizException.class,
                () ->controller.long2short(request),
                ExceptionEnum.REQUEST_BODY_INVALID.getErrorMessage());
        request.setLongUrl("fadsfadsf");
        assertThrows(BizException.class,
                () ->controller.long2short(request),
                ExceptionEnum.LONG_URL_INVALID.getErrorMessage());
        request.setLongUrl("http://www.baidu.com");
        assertEquals(controller.long2short(request).getErrorCode(), ExceptionEnum.SUCCESS.getErrorCode());
    }

    @Test
    void short2long() {
        BaseRequest request = new BaseRequest();
        assertThrows(BizException.class,
                () ->controller.short2long(request),
                ExceptionEnum.REQUEST_BODY_INVALID.getErrorMessage());
        request.setLongUrl("http://www.baidu.com");
        assertThrows(BizException.class,
                () ->controller.short2long(request),
                ExceptionEnum.REQUEST_BODY_INVALID.getErrorMessage());
        request.setShortUrl("aefadsf");
        assertThrows(BizException.class,
                () ->controller.short2long(request),
                ExceptionEnum.SHORT_URL_INVALID.getErrorMessage());
        request.setShortUrl("https://faof.cn/00000000");
        assertEquals(controller.long2short(request).getErrorCode(), ExceptionEnum.SUCCESS.getErrorCode());
    }
}
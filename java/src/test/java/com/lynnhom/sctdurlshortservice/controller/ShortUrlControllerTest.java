package com.lynnhom.sctdurlshortservice.controller;

import com.lynnhom.sctdurlshortservice.SctdUrlShortServiceApplicationTests;
import com.lynnhom.sctdurlshortservice.common.enums.RespCodeEnum;
import com.lynnhom.sctdurlshortservice.common.exception.BizException;
import com.lynnhom.sctdurlshortservice.common.utils.DateUtil;
import com.lynnhom.sctdurlshortservice.model.dto.UrlDto;
import com.lynnhom.sctdurlshortservice.service.ShortUrlService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;

/**
 * @description: ShortUrlController测试类
 * @author: Lynnhom
 * @create: 2021-11-11 19:26
 **/

public class ShortUrlControllerTest extends SctdUrlShortServiceApplicationTests {

    @Autowired
    private ShortUrlController shortUrlController;

    @Test
    @Order(7)
    void testCreate() {
        String oneYear = DateUtil.format(DateUtil.add(DateUtil.now(), 1, Calendar.YEAR), DateUtil.Format.HYPHEN_YYYYMMDDHHMMSS);
        UrlDto urlDto = UrlDto.builder()
                .appId("001")
                .token("aaabbbccc")
                .url("https://github.com/scdt-china/interview-assignments/tree/master/dev-ops")
                .expireDate(oneYear)
                .build();
        BizException bizException = Assertions.assertThrows(BizException.class, () -> shortUrlController.create(urlDto).getCode());
        Assertions.assertEquals(RespCodeEnum.TOKEN_INVALID.getCode(), bizException.getBizCode());

        urlDto.setToken("f903da788df34a9f1dd66e828942adb3");
        Assertions.assertEquals(RespCodeEnum.REQUEST_SUCCESS.getCode(),
                shortUrlController.create(urlDto).getCode());
        Assertions.assertEquals("https://www.lynnhom.net/hxU8n2",
                shortUrlController.create(urlDto).getData().getShortUrl());

    }

    @Test
    @Order(8)
    void testGetOriginalUrl() {
        Assertions.assertEquals("https://github.com/scdt-china/interview-assignments/tree/master/qa",
                shortUrlController.getOriginalUrl("https://www.lynnhom.net/eZEUKF").getData());
    }

}

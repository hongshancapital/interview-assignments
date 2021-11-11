package com.lynnhom.sctdurlshortservice.service;

import com.lynnhom.sctdurlshortservice.SctdUrlShortServiceApplicationTests;
import com.lynnhom.sctdurlshortservice.common.enums.RespCodeEnum;
import com.lynnhom.sctdurlshortservice.common.exception.BizException;
import com.lynnhom.sctdurlshortservice.common.utils.DateUtil;
import com.lynnhom.sctdurlshortservice.model.dto.UrlDto;
import com.lynnhom.sctdurlshortservice.model.dto.UrlResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Calendar;

/**
 * @description: ShortUrlService测试类
 * @author: Lynnhom
 * @create: 2021-11-11 15:11
 **/
public class ShortUrlServiceTest extends SctdUrlShortServiceApplicationTests {

    @Autowired
    private ShortUrlService shortUrlService;

    @Test
    @Order(5)
    void testCreate() {
        LocalDateTime now = LocalDateTime.now();
        String oneYear = DateUtil.format(DateUtil.add(DateUtil.now(), 1, Calendar.YEAR), DateUtil.Format.HYPHEN_YYYYMMDDHHMMSS);
        String fourYear = DateUtil.format(DateUtil.add(DateUtil.now(), 4, Calendar.YEAR), DateUtil.Format.HYPHEN_YYYYMMDDHHMMSS);

        UrlDto url = UrlDto.builder()
                .appId("001")
                .token("aaabbbccc")
                .url("htts://github.com/scdt-china/interview-assignments/tree/master/dev-ops")
                .expireDate(DateUtil.format(DateUtil.now(), DateUtil.Format.HYPHEN_YYYYMMDDHHMMSS))
                .build();
        // token校验
        BizException bizException = Assertions.assertThrows(BizException.class,
                () -> shortUrlService.create(url));
        Assertions.assertEquals(RespCodeEnum.TOKEN_INVALID.getCode(), bizException.getBizCode());

        // URL校验
        url.setToken("cbceef662976bd27d8ad6e86d30017cf");
        bizException = Assertions.assertThrows(BizException.class,
                () -> shortUrlService.create(url));
        Assertions.assertEquals(RespCodeEnum.URL_INVALID.getCode(), bizException.getBizCode());

        // 有效期校验
        url.setUrl("https://github.com/scdt-china/interview-assignments/tree/master/dev-ops");
        url.setToken("f903da788df34a9f1dd66e828942adb3");
        bizException = Assertions.assertThrows(BizException.class,
                () -> shortUrlService.create(url));
        Assertions.assertEquals(RespCodeEnum.EXPIRE_TIME_EARLIER_THAN_NOW.getCode(), bizException.getBizCode());

        // 有效期日期格式异常，取默认有效期
        url.setExpireDate("日期格式有误");
        UrlResponseDto urlResponse = shortUrlService.create(url);
        Assertions.assertEquals("https://github.com/scdt-china/interview-assignments/tree/master/dev-ops",
                urlResponse.getOriginalUrl());
        Assertions.assertEquals("https://www.lynnhom.net/hxU8n2",
                urlResponse.getShortUrl());
        Assertions.assertEquals(now.getYear()+5,
                urlResponse.getExpireDate().getYear());
        Assertions.assertEquals(now.getMonth(),
                urlResponse.getExpireDate().getMonth());

        // 存在相同的长链接，同一个appId创建的更新有效期
        UrlDto urlNew = UrlDto.builder()
                .appId("001")
                .token("f903da788df34a9f1dd66e828942adb3")
                .url("https://github.com/scdt-china/interview-assignments/tree/master/dev-ops")
                .expireDate(oneYear)
                .build();
        urlResponse = shortUrlService.create(urlNew);
        Assertions.assertEquals(now.getYear()+1,
                urlResponse.getExpireDate().getYear());
        Assertions.assertEquals(now.getMonth(),
                urlResponse.getExpireDate().getMonth());

        // 存在相同的长链接，不同appId创建的不更新有效期
        urlNew.setAppId("002");
        urlNew.setToken("1bb53c1b56b7237ca22f366a107d7774");
        urlNew.setExpireDate(fourYear);
        urlResponse = shortUrlService.create(urlNew);
        Assertions.assertEquals(now.getYear()+1,
                urlResponse.getExpireDate().getYear());
        Assertions.assertEquals(now.getMonth(),
                urlResponse.getExpireDate().getMonth());

        // 创建一个新的长链接
        urlNew.setUrl("https://github.com/scdt-china/interview-assignments/tree/master/frontend");
        urlNew.setToken("4740e17531102cbfa96053392d712f99");
        Assertions.assertEquals("https://www.lynnhom.net/eNOZWY", shortUrlService.create(urlNew).getShortUrl());
    }

    @Test
    @Order(6)
    void testGetOriginalUrl() {
        // 短链接格式错误
        BizException bizException = Assertions.assertThrows(BizException.class,
                () -> shortUrlService.getOriginalUrl(""));
        Assertions.assertEquals(RespCodeEnum.SHORT_URL_INVALID.getCode(), bizException.getBizCode());
        bizException = Assertions.assertThrows(BizException.class,
                () -> shortUrlService.getOriginalUrl("sftp://github.com/"));
        Assertions.assertEquals(RespCodeEnum.SHORT_URL_INVALID.getCode(), bizException.getBizCode());
        bizException = Assertions.assertThrows(BizException.class,
                () -> shortUrlService.getOriginalUrl("https://github.com/"));
        Assertions.assertEquals(RespCodeEnum.SHORT_URL_INVALID.getCode(), bizException.getBizCode());

        // 短链接获取校验
        Assertions.assertEquals("https://github.com/scdt-china/interview-assignments/tree/master/php",
                shortUrlService.getOriginalUrl("https://www.lynnhom.net/eYvlAF"));

        // 短链接不存在
        bizException = Assertions.assertThrows(BizException.class,
                () -> shortUrlService.getOriginalUrl("https://www.lynnhom.net/aaaaaa"));
        Assertions.assertEquals(RespCodeEnum.SHORT_URL_NOT_EXIST.getCode(), bizException.getBizCode());

    }



}

package org.faof.service;

import org.faof.exceptions.BizException;
import org.faof.exceptions.ExceptionEnum;
import org.faof.property.ApplicationProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ILongShortUrlServiceTest {

    static Logger logger = LoggerFactory.getLogger(ILongShortUrlServiceTest.class);

    @Autowired
    ILongShortUrlService longShortUrlService;
    @Autowired
    ApplicationProperty applicationProperty;

    @BeforeEach
    void initService() {
        longShortUrlService.initService();
    }

    @Test
    void getLong2ShortUrl() {
        String prefix = applicationProperty.getShortUrlPrefix();
        assertThrows(BizException.class,
                () -> longShortUrlService.getLong2ShortUrl(null),
                ExceptionEnum.REQUEST_BODY_INVALID.getErrorMessage());
        assertThrows(BizException.class,
                () -> longShortUrlService.getLong2ShortUrl(""),
                ExceptionEnum.LONG_URL_INVALID.getErrorMessage());
        assertThrows(BizException.class,
                () -> longShortUrlService.getLong2ShortUrl("fadsf"),
                ExceptionEnum.LONG_URL_INVALID.getErrorMessage());
        assertEquals(prefix + "00000000", longShortUrlService.getLong2ShortUrl("http://www.baidu.com"));
        assertEquals(prefix + "00000001", longShortUrlService.getLong2ShortUrl("http://www.baiduu.com"));
        assertEquals(prefix + "00000001", longShortUrlService.getLong2ShortUrl("http://www.baiduu.com"));
        assertEquals(prefix + "00000000", longShortUrlService.getLong2ShortUrl("http://www.baidu.com"));
        assertEquals(prefix + "00000002", longShortUrlService.getLong2ShortUrl("http://www.baiduuu.com"));
        assertEquals(prefix + "00000003", longShortUrlService.getLong2ShortUrl("http://www.baiduuuu.com"));
        assertEquals(prefix + "00000004", longShortUrlService.getLong2ShortUrl("http://www.baiduuuuu.com"));
        assertEquals(prefix + "00000005", longShortUrlService.getLong2ShortUrl("http://www.baiduuuuuu.com"));
        assertEquals(prefix + "00000006", longShortUrlService.getLong2ShortUrl("http://www.baiduuuuuuu.com"));
        assertEquals(prefix + "00000007", longShortUrlService.getLong2ShortUrl("http://www.baiduuuuuuuu.com"));
        assertEquals(prefix + "00000008", longShortUrlService.getLong2ShortUrl("http://www.baiduuuuuuuuu.com"));
        assertEquals(prefix + "00000009", longShortUrlService.getLong2ShortUrl("http://www.baiduuuuuuuuuu.com"));
        assertThrows(BizException.class,
                () -> longShortUrlService.getLong2ShortUrl("http://www.baiduuuuuuuuuuu.com"),
                ExceptionEnum.MEMORY_OVERFLOW.getErrorMessage());
    }

    @Test
    void getShort2LongUrl() {
        longShortUrlService.getLong2ShortUrl("http://www.baidu.com");
        longShortUrlService.getLong2ShortUrl("http://www.baiduu.com");
        longShortUrlService.getLong2ShortUrl("http://www.baiduuu.com");
        longShortUrlService.getLong2ShortUrl("http://www.baiduuuu.com");
        longShortUrlService.getLong2ShortUrl("http://www.baiduuuuu.com");
        assertThrows(BizException.class,
                () -> longShortUrlService.getShort2LongUrl(null),
                ExceptionEnum.REQUEST_BODY_INVALID.getErrorMessage());
        assertThrows(BizException.class,
                () -> longShortUrlService.getShort2LongUrl(""),
                ExceptionEnum.SHORT_URL_INVALID.getErrorMessage());
        assertThrows(BizException.class,
                () -> longShortUrlService.getShort2LongUrl("http://faof.cn/00000000"),
                ExceptionEnum.SHORT_URL_INVALID.getErrorMessage());
        assertThrows(BizException.class,
                () -> longShortUrlService.getShort2LongUrl("https://faof.cn/0000000"),
                ExceptionEnum.SHORT_URL_INVALID.getErrorMessage());
        assertThrows(BizException.class,
                () -> longShortUrlService.getShort2LongUrl("https://faof.cn/000000~0"),
                ExceptionEnum.SHORT_URL_INVALID.getErrorMessage());
        assertEquals("http://www.baidu.com", longShortUrlService.getShort2LongUrl("https://faof.cn/00000000"));
        assertEquals("http://www.baiduu.com", longShortUrlService.getShort2LongUrl("https://faof.cn/00000001"));
        assertEquals("http://www.baiduuu.com", longShortUrlService.getShort2LongUrl("https://faof.cn/00000002"));
        assertEquals("http://www.baiduuuu.com", longShortUrlService.getShort2LongUrl("https://faof.cn/00000003"));
        assertEquals("http://www.baiduuuuu.com", longShortUrlService.getShort2LongUrl("https://faof.cn/00000004"));
        assertEquals("", longShortUrlService.getShort2LongUrl("https://faof.cn/00000005"));
        assertEquals("", longShortUrlService.getShort2LongUrl("https://faof.cn/aZ000005"));
    }
}
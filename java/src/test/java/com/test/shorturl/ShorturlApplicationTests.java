package com.test.shorturl;

import com.test.shorturl.common.exception.ShortUrlException;
import com.test.shorturl.common.result.Result;
import com.test.shorturl.common.result.ResultCodeEnum;
import com.test.shorturl.controller.ShortUrlController;
import com.test.shorturl.service.ShortUrlService;
import com.test.shorturl.util.CommonUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ShorturlApplicationTests {
    private static Logger logger = LoggerFactory.getLogger(ShorturlApplicationTests.class);
    @SpyBean
    private ShortUrlController shortUrlController;
    @SpyBean
    private ShortUrlService shortUrlService;


    @Test
    public void testNormalOriginalAndShort() {
        Result result1 = shortUrlController.getLongToShort("111");
        logger.info(result1.getData().toString());
        Result result2 = shortUrlController.getShortToLong(result1.getData().toString());
        logger.info(result2.getData().toString());
        String shortUrl = shortUrlService.longToShort("111");
        Assertions.assertAll(
                () -> assertEquals(result1.getCode().toString(), "0"),
                () -> assertEquals(result2.getCode().toString(), "0"),
                () ->assertEquals(shortUrl,result1.getData().toString())
        );
    }

    @Test
    public void testOriginalIsNull() {
        Throwable throwable = assertThrows(ShortUrlException.class, () -> {
            shortUrlController.getLongToShort(null);
        });
        assertEquals(ResultCodeEnum.ORIGINAL_NOT_EMPTY.message(), throwable.getMessage());
    }

    @Test
    public void testOriginalIsEmpty() {
        Throwable throwable = assertThrows(ShortUrlException.class, () -> {
            shortUrlController.getLongToShort("");
        });
        assertEquals(ResultCodeEnum.ORIGINAL_NOT_EMPTY.message(), throwable.getMessage());
    }

    @Test
    public void testShortIsEmpty() {
        Throwable throwable = assertThrows(ShortUrlException.class, () -> {
            shortUrlController.getShortToLong("");
        });
        assertEquals(ResultCodeEnum.SHORT_NOT_EMPTY.message(), throwable.getMessage());
    }

    @Test
    public void testShortIsNull() {
        Throwable throwable = assertThrows(ShortUrlException.class, () -> {
            shortUrlController.getShortToLong(null);
        });
        assertEquals(ResultCodeEnum.SHORT_NOT_EMPTY.message(), throwable.getMessage());
    }

    @Test
    public void testExceedsZeroValue() {
        Throwable throwable = assertThrows(ShortUrlException.class, () -> {
            shortUrlService.longTransferToShortString(-1L);
        });
        assertEquals(ResultCodeEnum.EXCEEDS_ZERO_VALUE.message(), throwable.getMessage());
    }
    @Test
    public void testNormalSeq() {
        String shortUrl = shortUrlService.longTransferToShortString(63l);
        assertNotNull(shortUrl);
    }

    @Test
    public void testMaxSequence() {
        final Long MAX_SEQUENCE = (long) Math.pow(62, 8);
        String shortUrl = shortUrlService.longTransferToShortString(MAX_SEQUENCE);
        assertNotNull(shortUrl);
    }

    @Test
    public void testExceedsMaximumValue() {
        final Long MAX_SEQUENCE = (long) Math.pow(62, 8) + 1;
        Throwable throwable = assertThrows(ShortUrlException.class, () -> {
            shortUrlService.longTransferToShortString(MAX_SEQUENCE);
        });
        assertEquals(ResultCodeEnum.EXCEEDS_MAXIMUM_VALUE.message(), throwable.getMessage());
    }
    @Test
    public void testShortUrlNotExists() {
        Throwable throwable = assertThrows(ShortUrlException.class, () -> {
            shortUrlService.shortToLong("ttttt");
        });
        assertEquals(ResultCodeEnum.SHORT_URL_NOT_EXISTS.message(), throwable.getMessage());
    }
    @Test
    public void testShuffle() {
        String shuffle = CommonUtil.shuffle(CommonUtil.original);
        assertNotNull(shuffle);
    }
    @Test
    public void testCount() {
        Result result1 = shortUrlController.count();
        assertEquals(result1.getCode(), "0");
    }
}

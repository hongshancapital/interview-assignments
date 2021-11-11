package com.lynnhom.sctdurlshortservice.service;

import com.lynnhom.sctdurlshortservice.SctdUrlShortServiceApplicationTests;
import com.lynnhom.sctdurlshortservice.common.enums.RespCodeEnum;
import com.lynnhom.sctdurlshortservice.common.exception.BizException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

/**
 * @description: UrlMappingCacheService测试类
 * @author: Lynnhom
 * @create: 2021-11-09 20:51
 **/

public class UrlMappingCacheServiceTest extends SctdUrlShortServiceApplicationTests {

    @Test
    @Order(1)
    void testInsertMapping() {
        // 插入一个长链接
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneMinute = now.minusMinutes(-1L);
        Assertions.assertEquals("9Yebc8", urlMappingCacheService.insertMapping("001", "https://github.com/", oneMinute));

        // 再次插入相同的原链接，设置有效期失效，但原链接不更新
        urlMappingCacheService.insertMapping("001", "https://github.com/", now);
        Assertions.assertEquals("9Yebc8", urlMappingCacheService.getShortKey("https://github.com/").getShortKey());
    }

    @Test
    @Order(2)
    void testGetOriginalUrl() {
        // 短链接不存在
        BizException bizException = Assertions.assertThrows(BizException.class,
                () -> urlMappingCacheService.getOriginalUrl("aaaaaa"));
        Assertions.assertEquals(RespCodeEnum.SHORT_URL_NOT_EXIST.getCode(), bizException.getBizCode());

        // 短链接过期
        bizException = Assertions.assertThrows(BizException.class,
                () -> urlMappingCacheService.getOriginalUrl("hpoZMZ"));
        Assertions.assertEquals(RespCodeEnum.SHORT_URL_EXPIRED.getCode(), bizException.getBizCode());

        // 短链接存在
        Assertions.assertEquals("https://github.com/scdt-china/interview-assignments/tree/master/design",
                urlMappingCacheService.getOriginalUrl("hjgqWi"));
    }

    @Test
    @Order(3)
    void testGetShortKey() {
        // 长链接不存在
        Assertions.assertNull(urlMappingCacheService.getShortKey("https://baidu.com/"));
        // 长链接过期, 删除对应的映射
        Assertions.assertNull(urlMappingCacheService.getShortKey("https://github.com/scdt-china/interview-assignments"));
        BizException bizException = Assertions.assertThrows(BizException.class,
                () -> urlMappingCacheService.getOriginalUrl("hpoZMZ"));
        Assertions.assertEquals(RespCodeEnum.SHORT_URL_NOT_EXIST.getCode(), bizException.getBizCode());
        // 长链接存在
        Assertions.assertEquals("hAEu9V", urlMappingCacheService.getShortKey("https://github.com/scdt-china/interview-assignments/tree/master/java").getShortKey());
    }

    @Test
    @Order(4)
    void testUpdateExpireTime() {
        LocalDateTime expiredTime = LocalDateTime.now().minusMinutes(-1L);
        urlMappingCacheService.updateExpireTime("https://github.com/scdt-china/interview-assignments/tree/master/java", expiredTime);
        Assertions.assertEquals(expiredTime, urlMappingCacheService.getShortKey("https://github.com/scdt-china/interview-assignments/tree/master/java").getExpireTime());

        urlMappingCacheService.updateExpireTime("https://baidu.com/", expiredTime);
    }

    @Test
    @Order(100)
    void testClearExpireMapping() {
        urlMappingCacheService.clearExpireMapping();
    }
}

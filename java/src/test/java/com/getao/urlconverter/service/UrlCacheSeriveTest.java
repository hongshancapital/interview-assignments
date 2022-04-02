package com.getao.urlconverter.service;

import com.getao.urlconverter.config.CacheConfig;
import com.getao.urlconverter.controller.ConverterController;
import com.getao.urlconverter.dto.vo.UrlCacheVO;
import com.getao.urlconverter.service.impl.UrlCacheServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlCacheSeriveTest {

    @Resource
    CacheConfig cacheConfig;

    @Resource
    UrlCacheServiceImpl cacheService;

    private static final String resUrl = "a0000001";

    private static final String oriUrl = "www.google.com";

    @Test
    public void testCacheService() {
        UrlCacheVO vo = new UrlCacheVO(resUrl, oriUrl);
        assertThat(cacheService.putInCache(oriUrl, resUrl)).isEqualTo(vo);
        assertThat(cacheService.getCache(oriUrl)).isEqualTo(vo);
    }
}

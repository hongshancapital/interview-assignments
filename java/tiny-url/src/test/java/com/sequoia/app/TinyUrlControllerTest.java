package com.sequoia.app;

import com.google.common.collect.Sets;
import com.sequoia.domain.UrlRequest;
import com.sequoia.infrastructure.common.ApiResult;
import com.sequoia.infrastructure.common.StatusCodeEnum;
import com.sequoia.service.impl.TinyUrlServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;

/**
 * Descript:
 * File: com.sequoia.app.TinyUrlControllerTest
 * Author: daishengkai
 * Date: 2022/3/31
 * Copyright (c) 2022,All Rights Reserved.
 */
@Slf4j
@SpringBootTest
public class TinyUrlControllerTest {

    @SpyBean
    private TinyUrlServiceImpl tinyUrlService;

    @Autowired
    private TinyUrlController tinyUrlController;

    @Value("${tinyurl.prefix}")
    private String tinyUrlPrefix;

    static List<String> tinyUrlList = new ArrayList<>();
    static Map<String, Integer> originUrl2StatusCodeMap = new LinkedHashMap<>();
    static {
        originUrl2StatusCodeMap.put(null, StatusCodeEnum.PARAM_ERROR.getCode());
        originUrl2StatusCodeMap.put("", StatusCodeEnum.PARAM_ERROR.getCode());
        originUrl2StatusCodeMap.put("seq.com", StatusCodeEnum.OK.getCode());
        originUrl2StatusCodeMap.put("seq.com/sxxsfsfsdfsfss/fsfsfr/1231%", StatusCodeEnum.OK.getCode());
    }

    @Test
    public void testGetTinyUrl() {
        originUrl2StatusCodeMap.forEach((originUrl, statusCode) -> {
            try {
                ApiResult<String> result = tinyUrlController.getTinyUrl(new UrlRequest(originUrl));
//                Assertions.assertEquals(statusCode, result.getCode());
            } catch (Exception e) {
                log.error("异常 {} {}", originUrl, statusCode, e);
            }
        });

        ApiResult<String> result = tinyUrlController.getTinyUrl(null);
        Assertions.assertEquals(StatusCodeEnum.PARAM_ERROR.getCode(), result.getCode());

        UrlRequest urlRequest = new UrlRequest("seq.com");
        log.info("urlRequest: {}", urlRequest);
        result = tinyUrlController.getTinyUrl(urlRequest);
        Assertions.assertEquals(StatusCodeEnum.OK.getCode(), result.getCode());

        String originUrl = "sfsfsf";
        CompletableFuture<String> future = new CompletableFuture();
        future.completeExceptionally(new InterruptedException());
        Mockito.when(tinyUrlService.getTinyUrlFuture(originUrl)).thenReturn(future);
        result = tinyUrlController.getTinyUrl(new UrlRequest(originUrl));
        Assertions.assertEquals(StatusCodeEnum.SERVE_ERROR.getCode(), result.getCode());

        CompletableFuture<String> future1 = new CompletableFuture();
        future.completeExceptionally(new TimeoutException());
        Mockito.when(tinyUrlService.getTinyUrlFuture(originUrl)).thenReturn(future1);
        result = tinyUrlController.getTinyUrl(new UrlRequest(originUrl));
        Assertions.assertEquals(StatusCodeEnum.SERVE_TIMEOUT.getCode(), result.getCode());

        Assertions.assertNotEquals(ApiResult.ok("test", "执行成功"), ApiResult.error("异常"));
    }

    @Test
    public void testGetOriginUrl() {
        Sets.newHashSet("test.com", "tetsmm", "dfd.cn/fsfdsfs", "http://seq.cn/8s90uik").forEach(originUrl -> {
            tinyUrlList.add(tinyUrlController.getTinyUrl(new UrlRequest(originUrl)).getData());
        });

        for (String tinyUrl : tinyUrlList) {
            try {
                ApiResult result = tinyUrlController.getOriginUrl(new UrlRequest(tinyUrl));
//                Assertions.assertEquals(StatusCodeEnum.OK.getCode(), result.getCode());
            } catch (Exception e) {
                log.error("异常 {}", tinyUrl, e);
            }
        }

        ApiResult<String> result = tinyUrlController.getOriginUrl(new UrlRequest("seq.com"));
        Assertions.assertEquals(StatusCodeEnum.PARAM_ERROR.getCode(), result.getCode());

        result = tinyUrlController.getOriginUrl(new UrlRequest());
        Assertions.assertEquals(StatusCodeEnum.PARAM_ERROR.getCode(), result.getCode());

        result = tinyUrlController.getOriginUrl(null);
        Assertions.assertEquals(StatusCodeEnum.PARAM_ERROR.getCode(), result.getCode());

        result = tinyUrlController.getOriginUrl(new UrlRequest(tinyUrlPrefix + "12sfs4578fs@!"));
        Assertions.assertEquals(StatusCodeEnum.PARAM_ERROR.getCode(), result.getCode());

        String tinyCode = "sfsfsf";
        Mockito.when(tinyUrlService.getOriginUrl(tinyCode)).thenReturn(tinyCode);
        result = tinyUrlController.getOriginUrl(new UrlRequest(tinyCode));
        Assertions.assertEquals(StatusCodeEnum.SERVE_ERROR.getCode(), result.getCode());

        Mockito.when(tinyUrlService.getOriginUrl(tinyCode)).thenReturn(tinyCode);
        result = tinyUrlController.getOriginUrl(new UrlRequest(tinyCode));
        Assertions.assertEquals(StatusCodeEnum.SERVE_TIMEOUT.getCode(), result.getCode());

        Assertions.assertNotEquals(ApiResult.ok("test"), ApiResult.error("异常"));

        tinyUrlController.test();
    }

    @Test
    public void testUrlRequest() {
        UrlRequest urlRequest = new UrlRequest(null);
        log.info("hashCode: {}", urlRequest.hashCode());

        Assertions.assertEquals(false, urlRequest.equals(new UrlRequest("")));
    }

}

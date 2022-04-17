package com.tb.link.qatest.service;

import com.tb.link.client.ShortLinkService;
import com.tb.link.client.domain.Result;
import com.tb.link.client.domain.reponse.ShortLinkOriginResponse;
import com.tb.link.client.domain.reponse.ShortLinkResponse;
import com.tb.link.client.domain.request.AppName;
import com.tb.link.client.domain.request.LongLinkRequest;
import com.tb.link.domain.model.enums.ErrorCodeEnum;
import com.tb.link.start.Application;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author andy.lhc
 * @date 2022/4/17 10:47
 */
@Slf4j
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShortLinkServiceGenerateShortLinkTest {

    @Autowired
    private ShortLinkService shortLinkService;

    /**
     * 正确测试
     */
    @Test
    public void testGenerateShortLink() {

        LongLinkRequest request = LongLinkRequest.builder()
                .appName(
                        AppName.builder()
                                .appKey("common")
                                .build()
                )
                .expireTime(86400)
                .originLink("https://www.github.com/test?key=2&key3=5")
                .build();
        Result<ShortLinkResponse> result = shortLinkService.generalShortLink(request);
        log.info("testGenerateShortLink|data:{}", result.getData());
        Assertions.assertTrue(result.isSuccess());
        Assertions.assertTrue(result.getData() != null);
        Assertions.assertEquals(result.getErrorCode(), null);
        Assertions.assertEquals(result.getErrorMsg(), null);
    }

    @Test
    public void testNull() {
        Result<ShortLinkResponse> result = shortLinkService.generalShortLink(null);
        log.info("testNull|errorCode:{},errorMsg:{}", result.getErrorCode(), result.getErrorMsg());
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertEquals(ErrorCodeEnum.PARAM_IS_INVALID.getErrorCode(), result.getErrorCode());
        Assertions.assertEquals(ErrorCodeEnum.PARAM_IS_INVALID.getErrorMsg(), result.getErrorMsg());

        Result<ShortLinkOriginResponse> result1 = shortLinkService.recoverShortLink(null);
        log.info("testNull|errorCode:{},errorMsg:{}", result1.getErrorCode(), result1.getErrorMsg());
        Assertions.assertFalse(result1.isSuccess());
        Assertions.assertEquals(ErrorCodeEnum.PARAM_IS_INVALID.getErrorCode(), result1.getErrorCode());
        Assertions.assertEquals(ErrorCodeEnum.PARAM_IS_INVALID.getErrorMsg(), result1.getErrorMsg());
    }

    /**
     * 正确测试
     */
    @Test
    public void testGenerateShortLinkRetry() {

        LongLinkRequest request = LongLinkRequest.builder()
                .appName(
                        AppName.builder()
                                .appKey("common")
                                .build()
                )
                .expireTime(86400)
                .originLink("https://www.github.com/test?key=2&key3=5")
                .build();
        Result<ShortLinkResponse> result = shortLinkService.generalShortLink(request);
        log.info("testGenerateShortLinkRetry|data:{}", result.getData());
        Assertions.assertTrue(result.isSuccess());
        Assertions.assertTrue(result.getData() != null);
        Assertions.assertEquals(result.getErrorCode(), null);
        Assertions.assertEquals(result.getErrorMsg(), null);

        result = shortLinkService.generalShortLink(request);
        log.info("testGenerateShortLinkRetry|data2:{}", result.getData());
        Assertions.assertTrue(result.isSuccess());
        Assertions.assertTrue(result.getData() != null);
        Assertions.assertEquals(result.getErrorCode(), null);
        Assertions.assertEquals(result.getErrorMsg(), null);

        result = shortLinkService.generalShortLink(request);
        log.info("testGenerateShortLinkRetry|data3:{}", result.getData());
        Assertions.assertTrue(result.isSuccess());
        Assertions.assertTrue(result.getData() != null);
        Assertions.assertEquals(result.getErrorCode(), null);
        Assertions.assertEquals(result.getErrorMsg(), null);
    }

    /**
     * appName 为空
     */
    @Test
    public void testGenerateParamAppNameIsNull() {

        LongLinkRequest request = LongLinkRequest.builder()
                .appName(null)
                .expireTime(86400)
                .originLink("https://www.github.com/test?key=2&key3=5")
                .build();
        Result<ShortLinkResponse> result = shortLinkService.generalShortLink(request);
        log.info("testGenerateParamAppNameIsNull|errorCode:{},errorMsg:{}", result.getErrorCode(), result.getErrorMsg());
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertEquals(ErrorCodeEnum.PARAM_IS_INVALID.getErrorCode(), result.getErrorCode());
        Assertions.assertEquals(ErrorCodeEnum.PARAM_IS_INVALID.getErrorMsg(), result.getErrorMsg());
    }

    /**
     * appkey 不在白名单中
     */
    @Test
    public void testGenerateParamAppKeyNotInWhiteList() {

        LongLinkRequest request = LongLinkRequest.builder()
                .appName(AppName.builder()
                        .appKey("common1")
                        .build()
                )
                .expireTime(100)
                .originLink("https://www.github.com/test?key=2&key3=5")
                .build();
        Result<ShortLinkResponse> result = shortLinkService.generalShortLink(request);
        log.info("testGenerateParamAppKeyNotInWhiteList|errorCode:{},errorMsg:{}", result.getErrorCode(), result.getErrorMsg());
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertEquals(ErrorCodeEnum.APP_KEY_IS_NOT_IN_WHITELIST.getErrorCode(), result.getErrorCode());
        Assertions.assertEquals(ErrorCodeEnum.APP_KEY_IS_NOT_IN_WHITELIST.getErrorMsg(), result.getErrorMsg());
    }

    /**
     * expireTime 小于 0
     */
    @Test
    public void testGenerateParamExpireTimeIsNull() {

        LongLinkRequest request = LongLinkRequest.builder()
                .appName(AppName.builder()
                        .appKey("common")
                        .build()
                )
                .expireTime(0)
                .originLink("https://www.github.com/test?key=2&key3=5")
                .build();
        Result<ShortLinkResponse> result = shortLinkService.generalShortLink(request);
        log.info("testGenerateParamExpireTimeIsNull|errorCode:{},errorMsg:{}", result.getErrorCode(), result.getErrorMsg());
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertEquals(ErrorCodeEnum.PARAM_IS_INVALID.getErrorCode(), result.getErrorCode());
        Assertions.assertEquals(ErrorCodeEnum.PARAM_IS_INVALID.getErrorMsg(), result.getErrorMsg());
    }

    /**
     * originLink 为空
     */
    @Test
    public void testGenerateParamOriginLinkIsNull() {

        LongLinkRequest request = LongLinkRequest.builder()
                .appName(AppName.builder()
                        .appKey("common")
                        .build()
                )
                .expireTime(100)
                .originLink("")
                .build();
        Result<ShortLinkResponse> result = shortLinkService.generalShortLink(request);
        log.info("testGenerateParamOriginLinkIsNull|errorCode:{},errorMsg:{}", result.getErrorCode(), result.getErrorMsg());
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertEquals(ErrorCodeEnum.PARAM_IS_INVALID.getErrorCode(), result.getErrorCode());
        Assertions.assertEquals(ErrorCodeEnum.PARAM_IS_INVALID.getErrorMsg(), result.getErrorMsg());
    }

    /**
     * originLinkDomain 不在白名单中
     */
    @Test
    public void testGenerateParamOriginLinkDomainNotInWhiteList() {

        LongLinkRequest request = LongLinkRequest.builder()
                .appName(AppName.builder()
                        .appKey("common")
                        .build()
                )
                .expireTime(100)
                .originLink("https://www.github1.com/test?key=2&key3=5")
                .build();
        Result<ShortLinkResponse> result = shortLinkService.generalShortLink(request);
        log.info("testGenerateParamOriginLinkDomainNotInWhiteList|errorCode:{},errorMsg:{}", result.getErrorCode(), result.getErrorMsg());
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertEquals(ErrorCodeEnum.ORIGIN_SHORT_LINK_IS_NOT_IN_WHITELIST.getErrorCode(), result.getErrorCode());
        Assertions.assertEquals(ErrorCodeEnum.ORIGIN_SHORT_LINK_IS_NOT_IN_WHITELIST.getErrorMsg(), result.getErrorMsg());
    }

    /**
     * originLink 不是https/http
     */
    @Test
    public void testGenerateParamOriginLinkIsNotHttpOrHttps() {

        LongLinkRequest request = LongLinkRequest.builder()
                .appName(AppName.builder()
                        .appKey("common")
                        .build()
                )
                .expireTime(100)
                .originLink("hp://www.github1.com/test?key=2&key3=5")
                .build();
        Result<ShortLinkResponse> result = shortLinkService.generalShortLink(request);
        log.info("testGenerateParamOriginLinkIsNotHttpOrHttps|errorCode:{},errorMsg:{}", result.getErrorCode(), result.getErrorMsg());
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertEquals(ErrorCodeEnum.SUPPORT_HTTP_OR_HTTPS.getErrorCode(), result.getErrorCode());
        Assertions.assertEquals(ErrorCodeEnum.SUPPORT_HTTP_OR_HTTPS.getErrorMsg(), result.getErrorMsg());
    }

    /**
     * originLink 长度超过4096
     */
    @Test
    public void testGenerateParamOriginLinkMoreThan4096() {
        String str = "https://www.github1.com/test?key=2&key3=5&len=";
        for (int i = 0; i < 5000; i++) {
            str += i;
        }
        LongLinkRequest request = LongLinkRequest.builder()
                .appName(AppName.builder()
                        .appKey("common")
                        .build()
                )
                .expireTime(100)
                .originLink(str)
                .build();
        Result<ShortLinkResponse> result = shortLinkService.generalShortLink(request);
        log.info("testGenerateParamOriginLinkMoreThan4096|errorCode:{},errorMsg:{}", result.getErrorCode(), result.getErrorMsg());
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertEquals(ErrorCodeEnum.LONG_LINK_LENGTH_LESS_THAN_4096.getErrorCode(), result.getErrorCode());
        Assertions.assertEquals(ErrorCodeEnum.LONG_LINK_LENGTH_LESS_THAN_4096.getErrorMsg(), result.getErrorMsg());
    }


    /**
     * all param 为空
     */
    @Test
    public void testGenerateParamALlIsNull() {

        LongLinkRequest request = LongLinkRequest.builder()
                .appName(null)
                .expireTime(0)
                .originLink("")
                .build();
        Result<ShortLinkResponse> result = shortLinkService.generalShortLink(request);
        log.info("testGenerateParamALlIsNull|errorCode:{},errorMsg:{}", result.getErrorCode(), result.getErrorMsg());
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertEquals(ErrorCodeEnum.PARAM_IS_INVALID.getErrorCode(), result.getErrorCode());
    }


}

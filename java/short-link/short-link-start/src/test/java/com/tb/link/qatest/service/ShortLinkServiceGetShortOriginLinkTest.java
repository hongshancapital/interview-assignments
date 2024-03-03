package com.tb.link.qatest.service;

import com.tb.link.client.ShortLinkService;
import com.tb.link.client.domain.Result;
import com.tb.link.client.domain.reponse.ShortLinkOriginResponse;
import com.tb.link.client.domain.reponse.ShortLinkResponse;
import com.tb.link.client.domain.request.AppName;
import com.tb.link.client.domain.request.LongLinkRequest;
import com.tb.link.client.domain.request.ShortLinkRequest;
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
public class ShortLinkServiceGetShortOriginLinkTest {

    @Autowired
    private ShortLinkService shortLinkService;

    /**
     * 正确测试
     */
    @Test
    public void testRecoverShortLink() {
        AppName appName = AppName.builder()
                .appKey("common")
                .build();
        LongLinkRequest request = LongLinkRequest.builder()
                .appName(appName)
                .expireTime(86400)
                .originLink("https://www.github.com/test?key=2&key3=5")
                .build();
        Result<ShortLinkResponse> result = shortLinkService.generalShortLink(request);
        log.info("testRecoverShortLink|data:{}", result.getData());
        Assertions.assertTrue(result.isSuccess());
        Assertions.assertTrue(result.getData() != null);
        Assertions.assertEquals(result.getErrorCode(), null);
        Assertions.assertEquals(result.getErrorMsg(), null);

        ShortLinkRequest request1 = ShortLinkRequest.builder()
                .appName(appName)
                .shortLink(result.getData().getShortLink())
                .build();
        Result<ShortLinkOriginResponse> result1 = shortLinkService.recoverShortLink(request1);
        log.info("testRecoverShortLink|data2:{}", result1.getData());
        Assertions.assertTrue(result1.isSuccess());
        Assertions.assertTrue(result1.getData() != null);
        Assertions.assertEquals(result1.getErrorCode(), null);
        Assertions.assertEquals(result1.getErrorMsg(), null);
    }

    /**
     * appName 为空
     */
    @Test
    public void testRecoverParamAppNameIsNull() {

        ShortLinkRequest request = ShortLinkRequest.builder()
                .appName(null)
                .shortLink("https://scdt.cn/Zt9HpopB")
                .build();
        Result<ShortLinkOriginResponse> result = shortLinkService.recoverShortLink(request);
        log.info("testRecoverParamAppNameIsNull|errorCode:{},errorMsg:{}", result.getErrorCode(), result.getErrorMsg());
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertEquals(ErrorCodeEnum.PARAM_IS_INVALID.getErrorCode(), result.getErrorCode());
        Assertions.assertEquals(ErrorCodeEnum.PARAM_IS_INVALID.getErrorMsg(), result.getErrorMsg());
    }

    /**
     * appKey 不在白名单
     */
    @Test
    public void testRecoverParamAppKeyIsNotInWhiteList() {

        ShortLinkRequest request = ShortLinkRequest.builder()
                .appName(
                        AppName.builder()
                                .appKey("common1")
                                .build()
                )
                .shortLink("https://scdt.cn/Zt9HpopB")
                .build();
        Result<ShortLinkOriginResponse> result = shortLinkService.recoverShortLink(request);
        log.info("testRecoverParamAppKeyIsNotInWhiteList|errorCode:{},errorMsg:{}", result.getErrorCode(), result.getErrorMsg());
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertEquals(ErrorCodeEnum.APP_KEY_IS_NOT_IN_WHITELIST.getErrorCode(), result.getErrorCode());
        Assertions.assertEquals(ErrorCodeEnum.APP_KEY_IS_NOT_IN_WHITELIST.getErrorMsg(), result.getErrorMsg());
    }

    /**
     * shortLink 为空
     */
    @Test
    public void testRecoverParamShortLinkIsNull() {

        ShortLinkRequest request = ShortLinkRequest.builder()
                .appName(
                        AppName.builder()
                                .appKey("common")
                                .build()
                )
                .shortLink("")
                .build();
        Result<ShortLinkOriginResponse> result = shortLinkService.recoverShortLink(request);
        log.info("testRecoverParamAppNameIsNull|errorCode:{},errorMsg:{}", result.getErrorCode(), result.getErrorMsg());
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertEquals(ErrorCodeEnum.PARAM_IS_INVALID.getErrorCode(), result.getErrorCode());
        Assertions.assertEquals(ErrorCodeEnum.PARAM_IS_INVALID.getErrorMsg(), result.getErrorMsg());
    }

    /**
     * all param 为空
     */
    @Test
    public void testRecoverParamAllIsNull() {

        ShortLinkRequest request = ShortLinkRequest.builder()
                .appName(null)
                .shortLink(null)
                .build();
        Result<ShortLinkOriginResponse> result = shortLinkService.recoverShortLink(request);
        log.info("testRecoverParamAllIsNull|errorCode:{},errorMsg:{}", result.getErrorCode(), result.getErrorMsg());
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertEquals(ErrorCodeEnum.PARAM_IS_INVALID.getErrorCode(), result.getErrorCode());
        Assertions.assertEquals(ErrorCodeEnum.PARAM_IS_INVALID.getErrorMsg(), result.getErrorMsg());
    }

    /**
     * shortLink 不是 http / https
     */
    @Test
    public void testRecoverParamShortLinkIsNotHttpOrHttps() {

        ShortLinkRequest request = ShortLinkRequest.builder()
                .appName(
                        AppName.builder()
                                .appKey("common")
                                .build()
                )
                .shortLink("hps://scdt.cn/Zt9HpopB")
                .build();
        Result<ShortLinkOriginResponse> result = shortLinkService.recoverShortLink(request);
        log.info("testRecoverParamShortLinkIsNotHttpOrHttps|errorCode:{},errorMsg:{}", result.getErrorCode(), result.getErrorMsg());
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertEquals(ErrorCodeEnum.SUPPORT_HTTP_OR_HTTPS.getErrorCode(), result.getErrorCode());
        Assertions.assertEquals(ErrorCodeEnum.SUPPORT_HTTP_OR_HTTPS.getErrorMsg(), result.getErrorMsg());
    }

    /**
     * shortLink 域名不对
     */
    @Test
    public void testRecoverParamShortLinkPrefixIsInValid() {

        ShortLinkRequest request = ShortLinkRequest.builder()
                .appName(
                        AppName.builder()
                                .appKey("common")
                                .build()
                )
                .shortLink("https://scdt1.cn/Zt9HpopB")
                .build();
        Result<ShortLinkOriginResponse> result = shortLinkService.recoverShortLink(request);
        log.info("testRecoverParamShortLinkIsNotHttpOrHttps|errorCode:{},errorMsg:{}", result.getErrorCode(), result.getErrorMsg());
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertEquals(ErrorCodeEnum.SHORT_LINK_IS_NOT_IN_WHITELIST.getErrorCode(), result.getErrorCode());
        Assertions.assertEquals(ErrorCodeEnum.SHORT_LINK_IS_NOT_IN_WHITELIST.getErrorMsg(), result.getErrorMsg());
    }

    /**
     * shortLink 超过 8位
     */
    @Test
    public void testRecoverParamShortLinkLengthMoreThan8() {

        ShortLinkRequest request = ShortLinkRequest.builder()
                .appName(
                        AppName.builder()
                                .appKey("common")
                                .build()
                )
                .shortLink("https://scdt.cn/Zt9HpopB11")
                .build();
        Result<ShortLinkOriginResponse> result = shortLinkService.recoverShortLink(request);
        log.info("testRecoverParamShortLinkLengthMoreThan8|errorCode:{},errorMsg:{}", result.getErrorCode(), result.getErrorMsg());
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertEquals(ErrorCodeEnum.SHORT_LINK_LENGTH_LESS_THAN_8.getErrorCode(), result.getErrorCode());
        Assertions.assertEquals(ErrorCodeEnum.SHORT_LINK_LENGTH_LESS_THAN_8.getErrorMsg(), result.getErrorMsg());
    }

    /**
     * shortLink 找不到
     */
    @Test
    public void testRecoverParamShortLinkNotFound() {

        ShortLinkRequest request = ShortLinkRequest.builder()
                .appName(
                        AppName.builder()
                                .appKey("common")
                                .build()
                )
                .shortLink("https://scdt.cn/Zt9HpopA")
                .build();
        Result<ShortLinkOriginResponse> result = shortLinkService.recoverShortLink(request);
        log.info("testRecoverParamShortLinkNotFound|errorCode:{},errorMsg:{}", result.getErrorCode(), result.getErrorMsg());
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertEquals(ErrorCodeEnum.SHORT_LINK_NOT_FOUND.getErrorCode(), result.getErrorCode());
        Assertions.assertEquals(ErrorCodeEnum.SHORT_LINK_NOT_FOUND.getErrorMsg(), result.getErrorMsg());
    }

    /**
     * shortLink 已过期
     */
    @Test
    public void testRecoverParamShortLinkHasExpire() {

        AppName appName = AppName.builder()
                .appKey("common")
                .build();
        LongLinkRequest request = LongLinkRequest.builder()
                .appName(appName)
                .expireTime(1) //1秒
                .originLink("https://www.github.com/test?key=2&key3=5")
                .build();
        Result<ShortLinkResponse> result = shortLinkService.generalShortLink(request);
        log.info("testRecoverParamShortLinkHasExpire|data:{}", result.getData());
        Assertions.assertTrue(result.isSuccess());
        Assertions.assertTrue(result.getData() != null);
        Assertions.assertEquals(result.getErrorCode(), null);
        Assertions.assertEquals(result.getErrorMsg(), null);

        //停3秒过期
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }
        ShortLinkRequest request1 = ShortLinkRequest.builder()
                .appName(
                        AppName.builder()
                                .appKey("common")
                                .build()
                )
                .shortLink(result.getData().getShortLink())
                .build();
        Result<ShortLinkOriginResponse> result1 = shortLinkService.recoverShortLink(request1);
        log.info("testRecoverParamShortLinkHasExpire|errorCode:{},errorMsg:{}", result1.getErrorCode(), result1.getErrorMsg());
        Assertions.assertFalse(result1.isSuccess());
        Assertions.assertEquals(ErrorCodeEnum.SHORT_LINK_HAS_EXPIRE.getErrorCode(), result1.getErrorCode());
        Assertions.assertEquals(ErrorCodeEnum.SHORT_LINK_HAS_EXPIRE.getErrorMsg(), result1.getErrorMsg());
    }








}

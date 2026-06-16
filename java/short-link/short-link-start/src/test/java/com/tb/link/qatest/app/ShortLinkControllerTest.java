package com.tb.link.qatest.app;

import com.tb.link.app.web.ShortLinkController;
import com.tb.link.app.web.param.LongLinkParam;
import com.tb.link.app.web.param.ShorLinkParam;
import com.tb.link.app.web.view.ShortLinkOriginVO;
import com.tb.link.app.web.view.ShortLinkVO;
import com.tb.link.client.domain.Result;
import com.tb.link.client.domain.enums.ShortLinkErrorCodeEnum;
import com.tb.link.domain.model.enums.ErrorCodeEnum;
import com.tb.link.start.Application;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author andy.lhc
 * @date 2022/4/17 11:55
 */
@Slf4j
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShortLinkControllerTest {

    @Autowired
    private ShortLinkController shortLinkController;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Test
    public void testNull() {
        Result<ShortLinkVO> result = shortLinkController.createShortLink(null, null);
        Assertions.assertEquals(result.getErrorCode(), ShortLinkErrorCodeEnum.PARAM_IS_NOT_NULL.getErrorCode());

        Result<ShortLinkOriginVO> result1 = shortLinkController.recoverShortLink(null, null);
        Assertions.assertEquals(result1.getErrorCode(), ShortLinkErrorCodeEnum.PARAM_IS_NOT_NULL.getErrorCode());

    }

    /**
     * 生成短链正确case
     */
    @Test
    public void testCreateShortLink() {
        Result<ShortLinkVO> result = create();
        log.info("testCreateShortLink|data:{}", result.getData());
        Assertions.assertTrue(result.isSuccess());
        Assertions.assertTrue(result.getData() != null);
        Assertions.assertEquals(result.getErrorCode(), null);
        Assertions.assertEquals(result.getErrorMsg(), null);
    }

    /**
     * appKey is null 生成短链正确case
     */
    @Test
    public void testCreateShortLinkOfAppkeyIsNull() {
        Result<ShortLinkVO> result = createOfAppKeyIsNull();
        log.info("testCreateShortLink|data:{}", result.getData());
        Assertions.assertTrue(result.isSuccess());
        Assertions.assertTrue(result.getData() != null);
        Assertions.assertEquals(result.getErrorCode(), null);
        Assertions.assertEquals(result.getErrorMsg(), null);
    }

    private Result<ShortLinkVO> create() {
        LongLinkParam linkParam = new LongLinkParam();
        linkParam.setAppKey("common");
        linkParam.setExpireTime(100);
        linkParam.setOriginLink("https://www.github.com/test?key=2&key3=5");
        return shortLinkController.createShortLink(httpServletRequest, linkParam);
    }

    private Result<ShortLinkVO> createOfAppKeyIsNull() {
        LongLinkParam linkParam = new LongLinkParam();
        linkParam.setAppKey("");
        linkParam.setExpireTime(100);
        linkParam.setOriginLink("https://www.github.com/test?key=2&key3=5");
        return shortLinkController.createShortLink(httpServletRequest, linkParam);
    }

    /**
     * appKey 不合法
     */
    @Test
    public void testCreateShortLinkAppKeyIsInValid() {
        LongLinkParam linkParam = new LongLinkParam();
        linkParam.setAppKey("common1");
        linkParam.setExpireTime(100);
        linkParam.setOriginLink("https://www.github.com/test?key=2&key3=5");
        Result<ShortLinkVO> result = shortLinkController.createShortLink(httpServletRequest, linkParam);
        log.info("testCreateShortLinkAppKeyIsInValid|errorCode:{},errorMsg:{}", result.getErrorCode(), result.getErrorMsg());
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertEquals(ErrorCodeEnum.APP_KEY_IS_NOT_IN_WHITELIST.getErrorCode(), result.getErrorCode());
        Assertions.assertEquals(ErrorCodeEnum.APP_KEY_IS_NOT_IN_WHITELIST.getErrorMsg(), result.getErrorMsg());
    }

    /**
     * originLink 不在白名单
     */
    @Test
    public void testCreateShortLinkIsNotInWhiteList() {
        LongLinkParam linkParam = new LongLinkParam();
        linkParam.setAppKey("common");
        linkParam.setExpireTime(100);
        linkParam.setOriginLink("https://www.github1.com/test?key=2&key3=5");
        Result<ShortLinkVO> result = shortLinkController.createShortLink(httpServletRequest, linkParam);
        log.info("testCreateShortLinkIsNotInWhiteList|errorCode:{},errorMsg:{}", result.getErrorCode(), result.getErrorMsg());
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertEquals(ErrorCodeEnum.ORIGIN_SHORT_LINK_IS_NOT_IN_WHITELIST.getErrorCode(), result.getErrorCode());
        Assertions.assertEquals(ErrorCodeEnum.ORIGIN_SHORT_LINK_IS_NOT_IN_WHITELIST.getErrorMsg(), result.getErrorMsg());
    }


    /**
     * 短链转长链正确case
     */
    @Test
    public void testRecoverOriginLink() {

        Result<ShortLinkVO> result = create();
        log.info("testRecoverOriginLink|data:{}", result.getData());

        ShorLinkParam linkParam = new ShorLinkParam();
        linkParam.setAppKey("common");
        linkParam.setShortLink(result.getData().getShortLink());
        Result<ShortLinkOriginVO> result1 = shortLinkController.recoverShortLink(httpServletRequest, linkParam);
        log.info("testRecoverOriginLink|data1:{}", result1.getData());
        Assertions.assertTrue(result.isSuccess());
        Assertions.assertTrue(result1.getData() != null);
        Assertions.assertEquals(result1.getErrorCode(), null);
        Assertions.assertEquals(result1.getErrorMsg(), null);
    }

    /**
     * 短链转长链正确case
     */
    @Test
    public void testRecoverOriginLinkOfAppKeyIsNull() {

        Result<ShortLinkVO> result = createOfAppKeyIsNull();
        log.info("testRecoverOriginLink|data:{}", result.getData());

        ShorLinkParam linkParam = new ShorLinkParam();
        linkParam.setAppKey("common");
        linkParam.setShortLink(result.getData().getShortLink());
        Result<ShortLinkOriginVO> result1 = shortLinkController.recoverShortLink(httpServletRequest, linkParam);
        log.info("testRecoverOriginLink|data1:{}", result1.getData());
        Assertions.assertTrue(result.isSuccess());
        Assertions.assertTrue(result1.getData() != null);
        Assertions.assertEquals(result1.getErrorCode(), null);
        Assertions.assertEquals(result1.getErrorMsg(), null);
    }

    /**
     * 短链不存在
     */
    @Test
    public void testRecoverOriginLinkNotFound() {
        ShorLinkParam linkParam = new ShorLinkParam();
        linkParam.setAppKey("common");
        linkParam.setShortLink("https://scdt.cn/Zt9Hpop");
        Result<ShortLinkOriginVO> result = shortLinkController.recoverShortLink(httpServletRequest, linkParam);
        log.info("testRecoverOriginLinkNotFound|errorCode:{},errorMsg:{}", result.getErrorCode(), result.getErrorMsg());
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertEquals(ErrorCodeEnum.SHORT_LINK_NOT_FOUND.getErrorCode(), result.getErrorCode());
        Assertions.assertEquals(ErrorCodeEnum.SHORT_LINK_NOT_FOUND.getErrorMsg(), result.getErrorMsg());
    }

    @Test
    public void testT1() {
        String ok = shortLinkController.test().toString();
        Assertions.assertEquals(ok, "ok");
    }


}

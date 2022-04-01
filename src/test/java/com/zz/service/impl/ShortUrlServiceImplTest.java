package com.zz.service.impl;

import com.zz.BaseTest;
import com.zz.exception.BusinessException;
import com.zz.param.inparam.ShortUrlQueryParam;
import com.zz.param.inparam.ShortUrlStoreParam;
import com.zz.param.outparam.ShortUrlDTO;
import com.zz.service.ShortUrlService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zz
 * @version 1.0
 * @date 2022/4/1
 */
public class ShortUrlServiceImplTest extends BaseTest {
    @Autowired
    private ShortUrlService shortUrlService;

    @Test
    public void storeShortUrl() throws Exception {
        ShortUrlStoreParam param = new ShortUrlStoreParam();
        param.setHttpUrl("http://www.baidu.com");
        ShortUrlDTO shortUrlDTO = shortUrlService.storeShortUrl(param);
        Assert.assertTrue(shortUrlDTO != null && shortUrlDTO.getShortCode() != null);
    }

    @Test
    public void storeShortUrl_0() throws Exception {
        ShortUrlStoreParam param = new ShortUrlStoreParam();
        param.setHttpUrl("http://www.baidu.com?12=12");
        ShortUrlDTO shortUrlDTO = shortUrlService.storeShortUrl(param);
        Assert.assertTrue(shortUrlDTO != null && shortUrlDTO.getShortCode() != null);
    }

    @Test
    public void queryShortUrl() throws Exception {
        ShortUrlStoreParam param = new ShortUrlStoreParam();
        param.setHttpUrl("http://test.baidu.com");
        ShortUrlDTO shortUrlDTO = shortUrlService.storeShortUrl(param);

        ShortUrlQueryParam query = new ShortUrlQueryParam();
        query.setShortCode(shortUrlDTO.getShortCode());
        ShortUrlDTO res = shortUrlService.queryShortUrl(query);
        Assert.assertEquals(param.getHttpUrl(), res.getOriginHttpUrl());
    }

    @Test
    public void queryShortUrl_0() throws Exception {
        ShortUrlQueryParam query = new ShortUrlQueryParam();
        query.setShortCode("12345678");
        try {
            ShortUrlDTO res = shortUrlService.queryShortUrl(query);
        } catch (Exception e) {
            Assert.assertTrue(e.getClass() == BusinessException.class);
        }
    }
}
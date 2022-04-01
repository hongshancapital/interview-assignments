package com.zz.controller;

import com.zz.BaseTest;
import com.zz.exception.BusinessException;
import com.zz.param.Response;
import com.zz.param.inparam.ShortUrlQueryParam;
import com.zz.param.inparam.ShortUrlStoreParam;
import com.zz.param.outparam.ShortUrlDTO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @author zz
 * @version 1.0
 * @date 2022/4/1
 */
public class ShortUrlControllerTest extends BaseTest {

    @Autowired
    private ShortUrlController controller;

    @Test
    public void storeShortUrl() throws Exception {
        ShortUrlStoreParam param = new ShortUrlStoreParam();
        param.setHttpUrl("http://www.baidu.com");
        Response<ShortUrlDTO> res = controller.storeShortUrl(param);
        Assert.assertTrue(res != null && res.isSuccess() && res.getData().getShortCode() != null);
    }

    @Test
    public void queryShortUrl() throws Exception {
        ShortUrlQueryParam param = new ShortUrlQueryParam();
        param.setShortCode("123");
        try {
            Response<ShortUrlDTO> res = controller.queryShortUrl(param);
        } catch (Exception e) {
            Assert.assertTrue(e.getClass() == BusinessException.class);
        }
    }
}
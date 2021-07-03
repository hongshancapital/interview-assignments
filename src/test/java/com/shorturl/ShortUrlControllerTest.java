package com.shorturl;

import com.shorturl.common.CodeMsg;
import com.shorturl.controller.ShortUrlController;
import com.shorturl.domin.ShortUrlVo;
import com.shorturl.domin.response.WebResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
public class ShortUrlControllerTest {

    @Autowired
    private ShortUrlController shortUrlController;

    @Test
    public void createShortUrlFailedTest() {
        WebResponse<ShortUrlVo> webResponse = shortUrlController.createShortUrl("");
        Assert.assertNull(webResponse.getData());
    }

    @Test
    public void createShortUrlSuccessTest() {
        WebResponse<ShortUrlVo> webResponse = shortUrlController.createShortUrl(Data.LONG_URL);
        Assert.assertNotNull(webResponse.getData().getLongUrl());
    }

    @Test
    public void getLongUrlFailedTest() {
        WebResponse<ShortUrlVo> webResponse = shortUrlController.getLongUrl("www.g.cn");
        Assert.assertEquals(webResponse.getCode(), CodeMsg.SERVER_ERROR.getCode());
    }

    @Test
    public void checkMaxShortUrlTest() {
        WebResponse<ShortUrlVo> webResponse = shortUrlController.getLongUrl("httpwww.g.cn/dm5lbenr8s0k3liupag6m413sk");
        Assert.assertEquals(webResponse.getCode(), CodeMsg.BIND_ERROR.getCode());
    }

    @Test
    public void getLongUrlSuccessTest() {
        WebResponse<ShortUrlVo> webResponse = shortUrlController.createShortUrl(Data.LONG_URL);
        WebResponse<ShortUrlVo> shortUrlVoWebResponse = shortUrlController.getLongUrl(webResponse.getData().getShortUrl());
        Assert.assertNotNull(shortUrlVoWebResponse);
        Assert.assertNotNull(shortUrlVoWebResponse.getData());
        Assert.assertEquals(shortUrlVoWebResponse.getData().getLongUrl(), Data.LONG_URL);
    }

}

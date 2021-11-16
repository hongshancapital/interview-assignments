package com.liu.shorturl.controller;

import com.liu.shorturl.ShorturlApplication;
import com.liu.shorturl.dto.RestResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Description： TODO
 * Author: liujiao
 * Date: Created in 2021/11/11 17:32
 * email: liujiao@fcbox.com
 * Version: 0.0.1
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShorturlApplication.class)
public class ShortUrlControllerTest {

    @Autowired
    private ShortUrlController shortUrlController;


    @Test
    public void testShorUrl() {
        //保存长链接，返回对应短链接测试
        Assert.assertNull(shortUrlController.addShortUrlByUrl("").getData());
        Assert.assertNull(shortUrlController.addShortUrlByUrl(null).getData());

        String longUrl = "http://wwww.baidu.com";

        RestResponse<String> save3 = shortUrlController.addShortUrlByUrl(longUrl);
        Assert.assertEquals(save3.getSuccess(), true);

        String shortUrl = save3.getData();

        //根据短链接返回对应的长链接测试
        Assert.assertNull(shortUrlController.getUrlByShorUrl("").getData());
        Assert.assertNull(shortUrlController.getUrlByShorUrl(null).getData());

        RestResponse<String> longUrlRes = shortUrlController.getUrlByShorUrl(shortUrl);

        Assert.assertNotEquals(longUrlRes.getData(), longUrl);

    }


}
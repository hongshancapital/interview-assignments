package com.liu.shorturl.service.impl;

import com.liu.shorturl.ShorturlApplication;
import com.liu.shorturl.service.ShortUrlServicce;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Description： TODO
 * Author: liujiao
 * Date: Created in 2021/11/12 15:33
 * email: liujiao@fcbox.com
 * Version: 0.0.1
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShorturlApplication.class)
public class ShortUrlServiceImplTest {

    @Autowired
    private ShortUrlServicce shortUrlServicce;

    @Test
    public void testShortUrl () {
        //保存长链接，返回对应短链接测试
        Assert.assertNull(shortUrlServicce.addShortUrlByUrl(""));
        Assert.assertNull(shortUrlServicce.addShortUrlByUrl(null));

        String longUrl = "http://wwww.baidu.com";
        String shortUrlRes = shortUrlServicce.addShortUrlByUrl(longUrl);

        Assert.assertNotNull(shortUrlRes);
        String repeatShortUrlRes = shortUrlServicce.addShortUrlByUrl(longUrl);

        Assert.assertEquals(shortUrlRes, repeatShortUrlRes);


        //根据短链接返回对应的长链接测试
        Assert.assertNull(shortUrlServicce.getUrlByShortUrl(""));
        Assert.assertNull(shortUrlServicce.getUrlByShortUrl(null));

        String longUrlRes = shortUrlServicce.getUrlByShortUrl(shortUrlRes);

        Assert.assertEquals(longUrlRes, longUrl);
    }

}
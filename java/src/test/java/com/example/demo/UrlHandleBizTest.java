package com.example.demo;

import com.example.demo.application.biz.UrlHandleBiz;
import com.example.demo.utils.UrlUtils;
import com.google.common.base.VerifyException;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlHandleBizTest {

    @Autowired
    private UrlHandleBiz urlHandleBiz;

    @Test
    public void testTransferLongUrlSuccess() {
        String longUrl = "http://zheng/p/12332323/3232";
        String shortUrl = urlHandleBiz.transferLongUrl(longUrl);
        Assert.assertNotNull(shortUrl);
        Assert.assertEquals(shortUrl.length(), 20);
    }

    @Test(expected = IllegalStateException.class)
    public void testTransferLongUrlFail() {
        String longUrl = "";
        urlHandleBiz.transferLongUrl(longUrl);
    }

    @Test
    public void testGetLongUrlSuccess() {
        String longUrl = "http://zheng/p/12332323/3232";
        String shortUrl = urlHandleBiz.transferLongUrl(longUrl);
        String longUrl1 = urlHandleBiz.getLongUrl(shortUrl);
        Assert.assertEquals(longUrl, longUrl1);
    }

    @Test(expected = IllegalStateException.class)
    public void testGetLongUrlFail() {
        String shortUrl = "";
        urlHandleBiz.getLongUrl(shortUrl);
    }

    @Test
    public void testGetLongUrlForNull() {
        String shortUrl = "http://c.t.cn/abDASD";
        String longUrl = urlHandleBiz.getLongUrl(shortUrl);
        Assert.assertNull(longUrl);
    }

}

package com.hongshan.work;

import com.hongshan.work.controller.UrlController;
import com.hongshan.work.view.Result;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UrlControllerTest {
    @Autowired
    private UrlController controller;

    @Test
    public void urlToShortEmpty(){
        Result result = controller.getShortUrl("", "UTF-8");
        Assert.assertEquals(400, result.getCode().longValue());
    }

    @Test
    public void urlToShort(){
        Result result = controller.getShortUrl("http://www.baidu.com", "UTF-8");
        Assert.assertEquals(200, result.getCode().longValue());
    }

    @Test
    public void urlToShortNoProtocol(){
        Result result = controller.getShortUrl("www.baidu.com", "UTF-8");
        Assert.assertEquals(200, result.getCode().longValue());
    }

    @Test
    public void urlToShortHasChinese(){
        Result result = controller.getShortUrl("www.baike.baidu.com/item/中华人民共和国/106554?fr=aladdin", "UTF-8");
        System.out.println(result.getData());
        Assert.assertEquals(200, result.getCode().longValue());
    }


    @Test
    public void shortUrlNoExit(){
        Result res = controller.getLongUrl("ZZZZZZ");
        Assert.assertEquals("域名信息不存在", res.getMsg());
    }

    @Test
    public void longUrlNoError(){
        Result res = controller.getLongUrl("");
        Assert.assertEquals(400, res.getCode().longValue());
    }

    @Test
    public void longUrlOverStep(){
        Result res = controller.getLongUrl("1111111111");
        Assert.assertEquals(400, res.getCode().longValue());
    }

    @Test
    public void getLongUrl(){
        String longUrl ="http://www.baike.baidu.com";
        Result result = controller.getShortUrl(longUrl, "UTF-8");
        String shortUrl = String.valueOf(result.getData());
        controller.getLongUrl(shortUrl);
        Assert.assertEquals(longUrl, String.valueOf(controller.getLongUrl(shortUrl).getData()));
    }

}
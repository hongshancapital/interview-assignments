package com.scdt.shortenurl.interfaces;

import com.scdt.shortenurl.common.Response;
import com.scdt.shortenurl.interfaces.rest.ShortenUrlRestController;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Description
 * @Author chenlipeng
 * @Date 2022/3/7 12:54 下午
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShortenUrlRestControllerTest {

    @Resource
    private ShortenUrlRestController shortenUrlRestController;

    @Test
    public void testGenShortenUrl() {
        Response<String> response = shortenUrlRestController.genShortenUrl("www.baidu.com");
        Assert.assertEquals("qMnMze", response.getData());

        Response<String> response2 = shortenUrlRestController.genShortenUrl("");
        Assert.assertFalse(response2.getSuccess());
    }

    @Test
    public void testGetOriginalUrl() {
        Response<String> response = shortenUrlRestController.genShortenUrl("www.baidu.com");
        Assert.assertEquals("qMnMze", response.getData());

        Response<String> response2 = shortenUrlRestController.getOriginalUrl("qMnMze");
        Assert.assertEquals("www.baidu.com", response2.getData());

        Response<String> response3 = shortenUrlRestController.getOriginalUrl("");
        Assert.assertFalse(response3.getSuccess());
    }

}

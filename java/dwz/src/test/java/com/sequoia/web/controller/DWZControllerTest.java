package com.sequoia.web.controller;

import com.sequoia.web.view.Result;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.UnsupportedEncodingException;

@SpringBootTest
public class DWZControllerTest {
    @Autowired
    private DWZController controller;

    private static final String originalUrl = "http://www.baidu.com";

    @Test
    public void urlToShort(){
        Result result = controller.urlToShort(originalUrl, "UTF-8");
        Assert.assertEquals("D6Mocpge", result.getData());
    }

    @Test
    public void urlToShortWithChinese(){
        Result result = controller.urlToShort("https://www.baidu.com/s?wd=中国", "UTF-8");
        Assert.assertEquals("JRIP12tk", result.getData());
    }

    @Test
    public void urlToShortNoProtocol(){
        Result result = controller.urlToShort("www.baidu.com", "UTF-8");
        Assert.assertEquals("D6Mocpge", result.getData());
    }


    @Test
    public void urlToShortEmpty(){
        Result result = controller.urlToShort("", "UTF-8");
        Assert.assertEquals(400, result.getCode().longValue());
    }

    @Test
    public void redirectToLong() throws UnsupportedEncodingException {
        MockHttpServletResponse response = new MockHttpServletResponse();
        String result = controller.redirectToLong("D6Mocpge", response);
        Assert.assertEquals("redirect:" + originalUrl, result);

        String result2 = controller.redirectToLong("ZZZZZZ", response);
        Assert.assertEquals(null, result2);
        Assert.assertEquals("{\"code\":500,\"msg\":\"不存在该链接\",\"data\":\"ZZZZZZ\"}", response.getContentAsString().replaceFirst("\n","").replaceFirst("\r",""));
    }
}

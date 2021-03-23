package com.scdt.exam;

import com.scdt.exam.support.ResponseResult;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UrlControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void testGetShortUrl() throws Exception {
        String url = "http://www.baidu.com";
        ResponseResult context = testRestTemplate.getRestTemplate().getForObject("/url/short?url=" + url, ResponseResult.class);
        System.out.println(context);
        Assert.assertEquals(context.getData(), "ksEZ0000");
    }

    @Test
    public void testGetFullUrl() throws Exception {
        String code = "ksEZ0000";
        ResponseResult context = testRestTemplate.getRestTemplate().getForObject("/url/full?code=" + code, ResponseResult.class);
        System.out.println(context);
        Assert.assertEquals(context.getData(), "http://www.baidu.com");
    }
}

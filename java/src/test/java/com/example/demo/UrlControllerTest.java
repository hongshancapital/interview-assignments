/**
 * @(#)UrlControllerTest.java, 12月 27, 2021.
 * <p>
 * Copyright 2021 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.example.demo;

import com.example.demo.api.*;
import com.example.demo.constant.DemoErrorEnum;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zhengyin
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlControllerTest {

    @Autowired
    private UrlController urlController;

    @Test
    public void testTransferLongUrl() {
        String longUrl = "http://zheng/p/12332323/3232";
        Response<String> response = urlController.transferLongUrl(longUrl);
        Assert.assertEquals(Integer.parseInt(response.getCode()), DemoErrorEnum.SUCCESS.getCode());
        Assert.assertTrue(response.getResult().contains("http://c.t.cn/"));
    }

    @Test
    public void testTransferLongUrlFail() {
        String longUrl = "";
        Response<String> response = urlController.transferLongUrl(longUrl);
        Assert.assertNotEquals(Integer.parseInt(response.getCode()), DemoErrorEnum.SUCCESS.getCode());
    }

    @Test
    public void testGetLongUrl() {
        String longUrl = "http://zheng/p/12332323/3232";
        Response<String> response = urlController.transferLongUrl(longUrl);
        Response<String> response1 = urlController.getLongUrl(response.getResult());
        Assert.assertEquals(Integer.parseInt(response1.getCode()), DemoErrorEnum.SUCCESS.getCode());
        Assert.assertEquals(response1.getResult(),longUrl);
    }

    @Test
    public void testGetLongUrlFail() {
        String shortUrl = "http://zheng/p/12332323/3232";
        Response<String> response1 = urlController.getLongUrl(shortUrl);
        Assert.assertNotEquals(Integer.parseInt(response1.getCode()), DemoErrorEnum.SUCCESS.getCode());
    }

}
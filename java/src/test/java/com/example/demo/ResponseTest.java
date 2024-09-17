/**
 * @(#)ResponseTest.java, 12月 27, 2021.
 * <p>
 * Copyright 2021 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.example.demo;

import com.example.demo.api.Response;
import com.example.demo.constant.DemoErrorEnum;
import org.junit.*;

/**
 * @author zhengyin
 */
public class ResponseTest {

    @Test
    public void testCreateFail() {
        Response response = Response.createFail(DemoErrorEnum.PARAM_ERROR.toString());
        Assert.assertEquals(Integer.parseInt(response.getCode()), DemoErrorEnum.PARAM_ERROR.getCode());
        Assert.assertEquals(response.getMessage(), DemoErrorEnum.PARAM_ERROR.getMessage());
    }

    @Test
    public void testCreateSuccess() {
        Response<String> response = Response.createSuccess("success");
        Assert.assertEquals(Integer.parseInt(response.getCode()), DemoErrorEnum.SUCCESS.getCode());
        Assert.assertEquals(response.getMessage(), DemoErrorEnum.SUCCESS.getMessage());
    }


}
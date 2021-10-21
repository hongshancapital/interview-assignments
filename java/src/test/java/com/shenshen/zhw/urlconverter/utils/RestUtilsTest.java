package com.shenshen.zhw.urlconverter.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestUtilsTest {

    @Test
    void successJSON() {
        JSONObject json = RestUtils.successJSON("ok");
        Assert.assertEquals("ok", json.getString("msg"));
    }

    @Test
    void testSuccessJSON() {
        JSONObject json = RestUtils.successJSON("ok", "");
        Assert.assertEquals("ok", json.getString("msg"));
    }

    @Test
    void success() {
        String str = RestUtils.success("ok", new Object());
        JSONObject json = JSON.parseObject(str);
        Assert.assertEquals("ok", json.getString("msg"));

    }

    @Test
    void errorJSON() {
        JSONObject json = RestUtils.errorJSON(10, "error");
        Assert.assertEquals(10L, json.getLongValue("errorCode"));
    }

    @Test
    void testErrorJSON() {
        JSONObject json = RestUtils.errorJSON( "error");
        Assert.assertEquals("error", json.getString("msg"));
    }


    @Test
    void testError() {
        String str = RestUtils.error(10,"error");
        JSONObject json = JSON.parseObject(str);
        Assert.assertEquals(10L, json.getLongValue("errorCode"));
    }
}
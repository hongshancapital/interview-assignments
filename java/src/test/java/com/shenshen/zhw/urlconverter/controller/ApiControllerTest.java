package com.shenshen.zhw.urlconverter.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ApiControllerTest {
    @Autowired
    private ApiController controller;

    @Test
    void index() {
        Assert.assertEquals("服务启动成功", controller.index());
    }

    @Test
    void get() {

        String result = controller.get("");
        JSONObject obj = JSON.parseObject(result);
        Assert.assertTrue("false".equals(obj.getString("success")));

        result = controller.get(null);
        obj = JSON.parseObject(result);
        Assert.assertTrue("false".equals(obj.getString("success")));

        result = controller.get("1111");
        obj = JSON.parseObject(result);
        Assert.assertTrue("true".equals(obj.getString("success")));

    }

    @Test
    void save() {
        String result = controller.save("");
        JSONObject obj = JSON.parseObject(result);
        Assert.assertTrue("false".equals(obj.getString("success")));

        result = controller.save(null);
        obj = JSON.parseObject(result);
        Assert.assertTrue("false".equals(obj.getString("success")));

        result = controller.save("http://localhost");
        obj = JSON.parseObject(result);
        Assert.assertTrue("true".equals(obj.getString("success")));
    }
}
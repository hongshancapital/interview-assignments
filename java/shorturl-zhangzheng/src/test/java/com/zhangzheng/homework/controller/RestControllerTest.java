package com.zhangzheng.homework.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhangzheng.homework.ApplicationTests;
import com.zhangzheng.homework.controller.request.ShortUrlRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author zhangzheng
 * @version 1.0
 * @description: TODO
 * @date 2021/10/11 下午6:22
 */
@WebAppConfiguration
public class RestControllerTest extends ApplicationTests {

    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    @Before()  //这个方法在每个方法执行之前都会执行一遍
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();  //初始化MockMvc对象
    }

    @Test
    public void generate() {
        String url="/url/generate";
        ShortUrlRequest request = new ShortUrlRequest();
        request.setLongUrl("http://www.dadf.com/ewrw/23ddfa/asdf");

        try {
            mockMvc.perform(MockMvcRequestBuilders.post(url)
                                                  .accept(MediaType.APPLICATION_JSON)
                                                  .contentType(MediaType.APPLICATION_JSON)
                                                  .content(JSONObject.toJSONString(request).getBytes())
            ).andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void revert() {
        String url="/url/revert";
        ShortUrlRequest request = new ShortUrlRequest();
        request.setShortUrl("Uie3D");

        try {
            mockMvc.perform(MockMvcRequestBuilders.post(url)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JSONObject.toJSONString(request).getBytes())
            ).andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();
        } catch (Exception e) {
            Assert.fail();
        }
    }
}

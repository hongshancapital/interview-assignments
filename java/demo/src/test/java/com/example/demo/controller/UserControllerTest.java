package com.example.demo.controller;

import com.example.demo.DemoApplication;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shortUrlTransformLongUrl() throws Exception{
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.post("/UrlTransform/longUrlTransformShortUrl")
                        .param("url","http:www.taobao.com/getabc")
                )
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        String responseString = mvcResult.getResponse().getContentAsString();

        Assert.assertEquals("请求正确", 200, status);
    }


    @Test
    void longUrlTransformShortUrl()  throws Exception{
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.post("/UrlTransform/ShortUrlTransformLongUrl")
                        .param("url","ssss")
                )
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        String responseString = mvcResult.getResponse().getContentAsString();
        Assert.assertEquals("请求正确", 200, status);
    }
}
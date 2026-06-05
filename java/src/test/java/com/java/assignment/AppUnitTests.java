package com.java.assignment;

import com.alibaba.fastjson.JSONObject;
import com.java.assignment.web.controller.ApiOperatorController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class AppUnitTests {

    @Autowired
    private ApiOperatorController controller;

    private String longUrl = "https://echarts.baidu.com/examples/editor.html?c=";

    private String shortUrl;

    @Autowired
    private WebApplicationContext wac;

    //伪造一个MVC的环境，伪造的环境不会启动tomcat，
    // 所以测试用例会启动的很快
    private MockMvc mockMvc;

    //在测试之前注册mockmvc
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void longToShort() throws Exception {
//        mockMvc.perform(
//                MockMvcRequestBuilders.get("/url/longToShort")
//                        .param("longUrl", longUrl)
//        )
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(MockMvcResultHandlers.print())
//        ;
        //longToShort
        JSONObject obj = (JSONObject) controller.longToShort(longUrl).getData();
        Assert.assertNotNull(obj);
        shortUrl = obj.get("shortUrl").toString();
        Assert.assertNotNull(shortUrl);
        //shortToLong
        String s = controller.getLongUrl(shortUrl, null).getData().toString();
        Assert.assertNotNull(s);
        Assert.assertEquals(s, longUrl);
    }

}

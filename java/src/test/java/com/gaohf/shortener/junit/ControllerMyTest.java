package com.gaohf.shortener.junit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ControllerMyTest {

    private MockMvc mockMvc; // 模拟MVC对象，通过MockMvcBuilders.webAppContextSetup(this.wac).build()初始化。

    @Autowired
    private WebApplicationContext wac; // 注入WebApplicationContext

    @Before // 在测试开始前初始化工作
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @After
    public void after() {
        System.out.println("执行 after() 方法");
    }

    @Test
    public void create() throws Exception{
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/v1/create")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("url","https://www.baidu.com"))
                        .andReturn();// 返回执行请求的结果
        System.out.println(result.getResponse().getContentAsString());
    }


    @Test
    public void getUrl() throws Exception{
        MvcResult result1 = mockMvc.perform(MockMvcRequestBuilders.get("/v1/geUrl")
                .param("id","848fb2f5")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andReturn();// 返回执行请求的结果
        System.out.println(result1.getResponse().getContentAsString());
    }
}

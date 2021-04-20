package com.example.demo;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.service.UrlGeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * UrlGeneratorController Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>2021/04/20</pre>
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class UrlGeneratorControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UrlGeneratorService urlGeneratorService;

    @Before
    public void before() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @After
    public void after() throws Exception {
    }


    @Test
    public void testGenerateShortUrl() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/generate")
                .param("url", "https://github.com/scdt-china/interview-assignments/tree/master/java")
                ).andReturn();

        int status = mvcResult.getResponse().getStatus();
        String responseStr = mvcResult.getResponse().getContentAsString();
        JSONObject jsonObject = JSONObject.parseObject(responseStr);
        Assert.assertEquals("请示错误", 200, status);
        Assert.assertNotNull("短链接生成失败", jsonObject.get("data"));

        String shortUrl = jsonObject.getString("data");
        log.info("生成的短链接为：{}", shortUrl);
    }


    @Test
    public void testFindOriginUrl() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/originUrl")
                        .param("shortUrl", "M7ve2a")
                ).andReturn();

        int status = mvcResult.getResponse().getStatus();
        String responseStr = mvcResult.getResponse().getContentAsString();
        JSONObject jsonObject = JSONObject.parseObject(responseStr);
        Assert.assertEquals("请示错误", 200, status);
        Assert.assertNotNull("获取原始链接失败", jsonObject.get("data"));
        log.info("获取原始链接：{}", jsonObject.getString("data"));
    }


} 

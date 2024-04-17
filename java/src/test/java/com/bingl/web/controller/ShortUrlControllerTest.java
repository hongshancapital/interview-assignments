package com.bingl.web.controller;


import com.bingl.web.ApplicationTestBase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

// 配置Spring中的测试环境
public class ShortUrlControllerTest extends ApplicationTestBase {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void before() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();   //构造MockMvc
    }
    @Test
    public void testSaveShortUrl() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/saveShortUrl/{longUrl}","lvgasdfsdfserwerdgxfhfgdhfghfghng")
                .accept(MediaType.APPLICATION_JSON))
                //等同于Assert.assertEquals(200,status);
                .andExpect(MockMvcResultMatchers.status().isOk())
                //等同于 Assert.assertEquals("hello world!",content);
                .andExpect(MockMvcResultMatchers.content().string("Bl7VJgB5"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        //得到返回代码
        int status = mvcResult.getResponse().getStatus();
        //得到返回结果
        String content = mvcResult.getResponse().getContentAsString();
        //断言，判断返回代码是否正确
        Assert.assertEquals(200, status);
        //断言，判断返回的值是否正确
        Assert.assertEquals("Bl7VJgB5", content);
    }

    @Test
    public void testReadLongUrl() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/readLongUrl/{shortUrl}","Bl7VJgB5")
                .accept(MediaType.APPLICATION_JSON))
                //等同于Assert.assertEquals(200,status);
                .andExpect(MockMvcResultMatchers.status().isOk())
                //等同于 Assert.assertEquals("hello world!",content);
                .andExpect(MockMvcResultMatchers.content().string("未知的短域名Bl7VJgB5"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        //得到返回代码
        int status = mvcResult.getResponse().getStatus();
        //得到返回结果
        String content = mvcResult.getResponse().getContentAsString();
        //断言，判断返回代码是否正确
        Assert.assertEquals(200, status);
        //断言，判断返回的值是否正确
        Assert.assertEquals("未知的短域名Bl7VJgB5", content);
    }
}

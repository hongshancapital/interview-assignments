package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.BaseSpringBootTest;
import com.example.demo.core.web.ApiResponse;
import com.example.demo.service.ShortUrlService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * @ClassName ShortUrlControllerTest
 * @Description 短域名服务测试用例
 * @Author gongguanghui
 * @Date 2021/11/26 12:11 PM
 * @Version 1.0
 **/
@SpringBootTest
public class ShortUrlControllerTest extends BaseSpringBootTest {

    @Autowired
    ShortUrlController shortUrlController;

    @Autowired
    private ShortUrlService shortUrlService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(shortUrlController).build();
    }

    /**
     * 测试短域名存储接口
     */
    @Test
    public void testStoreMethodSuccess() throws Exception {
        String url = "http://localhost:8080/swagger-ui.html";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/shortUrl/store")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("url", url)
                .param("check", "true"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        logger.info(mvcResult.getResponse().getContentAsString());
        String responseResult = mvcResult.getResponse().getContentAsString();
        ApiResponse response = JSONObject.parseObject(responseResult, ApiResponse.class);

        Assert.assertEquals("200", response.getCode().toString());
        Assert.assertEquals("调用成功", response.getMessage());
    }

    /**
     * 长链接地址非法
     */
    @Test
    public void testStoreMethodCheckFalse() throws Exception {
        String url = "http://1";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/shortUrl/store")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("url", url)
                .param("check", "true"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        logger.info(mvcResult.getResponse().getContentAsString());
        String responseResult = mvcResult.getResponse().getContentAsString();
        ApiResponse response = JSONObject.parseObject(responseResult, ApiResponse.class);

        Assert.assertEquals("1000", response.getCode().toString());
        Assert.assertEquals("链接地址非法，请重新输入!", response.getMessage());
    }

    /**
     * 长链接地址为空
     */
    @Test
    public void testStoreMethodUrlBlank() throws Exception {
        String url = "";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/shortUrl/store")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("url", url)
                .param("check", "false"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        logger.info(mvcResult.getResponse().getContentAsString());
        String responseResult = mvcResult.getResponse().getContentAsString();
        ApiResponse response = JSONObject.parseObject(responseResult, ApiResponse.class);

        Assert.assertEquals("1001", response.getCode().toString());
        Assert.assertEquals("链接地址为空，请重新输入!", response.getMessage());
    }

    /**
     * 测试短域名获取接口
     */
    @Test
    public void testGetMethodSuccess() throws Exception {
        //1.先存储一个短链接，然后通过短链接获取长链接
        String longUrl = "http://localhost:8080/swagger-ui.html";
        String url = shortUrlService.storeShortUrl(longUrl);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/shortUrl/get")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("url", url))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        logger.info(mvcResult.getResponse().getContentAsString());
        String responseResult = mvcResult.getResponse().getContentAsString();
        ApiResponse response = JSONObject.parseObject(responseResult, ApiResponse.class);

        Assert.assertEquals("200", response.getCode().toString());
        Assert.assertEquals("调用成功", response.getMessage());
    }

    /**
     * 测试短域名不存在的场景
     */
    @Test
    public void testGetMethodNotExist() throws Exception {
        String notExistUrl = "CS6ZLT1";
        String longUrl = "http://localhost:8080/swagger-ui.html";
        String url = shortUrlService.storeShortUrl(longUrl);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/shortUrl/get")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("url", notExistUrl))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        logger.info(mvcResult.getResponse().getContentAsString());
        String responseResult = mvcResult.getResponse().getContentAsString();
        ApiResponse response = JSONObject.parseObject(responseResult, ApiResponse.class);

        Assert.assertEquals("1002", response.getCode().toString());
        Assert.assertEquals("链接地址不存在!", response.getMessage());
    }


    /**
     * 短链接地址为空
     */
    @Test
    public void testGetMethodUrlBlank() throws Exception {
        String url = "";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/shortUrl/get")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("url", url))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        logger.info(mvcResult.getResponse().getContentAsString());
        String responseResult = mvcResult.getResponse().getContentAsString();
        ApiResponse response = JSONObject.parseObject(responseResult, ApiResponse.class);

        Assert.assertEquals("1001", response.getCode().toString());
        Assert.assertEquals("链接地址为空，请重新输入!", response.getMessage());
    }
}
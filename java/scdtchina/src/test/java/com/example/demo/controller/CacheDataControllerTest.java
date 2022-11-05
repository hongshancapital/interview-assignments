package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.BaseSpringBootTest;
import com.example.demo.controller.inner.CacheDataController;
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
 * @ClassName CacheDataControllerTest
 * @Description TODO
 * @Author gongguanghui
 * @Date 2021/11/26 6:43 PM
 * @Version 1.0
 **/
@SpringBootTest
public class CacheDataControllerTest extends BaseSpringBootTest {
    @Autowired
    private ShortUrlService shortUrlService;

    @Autowired
    private CacheDataController cacheDataController;


    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(cacheDataController).build();
    }

    /**
     * 测试短域名存储接口
     */
    @Test
    public void testStoreMethodSuccess() throws Exception {
        //1.先存储一个短链接，然后通过短链接获取长链接
        String longUrl = "http://localhost:8080/swagger-ui.html";
        shortUrlService.storeShortUrl(longUrl);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/cache/getAllCacheDatas")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        logger.info(mvcResult.getResponse().getContentAsString());
    }

    /**
     * 长链接地址非法
     */
    @Test
    public void testStoreMethodCheckFalse() throws Exception {
        //1.先存储一个短链接，然后通过短链接获取长链接
        String longUrl = "http://localhost:8080/swagger-ui.html";
        shortUrlService.storeShortUrl(longUrl);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/cache/getSize")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        logger.info(mvcResult.getResponse().getContentAsString());
        String responseResult = mvcResult.getResponse().getContentAsString();
        ApiResponse response = JSONObject.parseObject(responseResult, ApiResponse.class);
        Assert.assertEquals(1, response.getData());

    }
}

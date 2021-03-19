package com.sxg.shortUrl.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.sxg.shortUrl.controller.UrlController;
import com.sxg.shortUrl.service.UrlService;
import com.sxg.shortUrl.utils.RedisUtil;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DisplayName("短链接控制器层验证")
class UrlControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private UrlService urlService;

    @Autowired
    private RedisUtil redisUtil;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new UrlController(urlService, redisUtil)).build();
    }

    @Test
    @DisplayName("创建短链接")
    void createSurl() throws Exception {
        MockHttpServletRequestBuilder url1 = MockMvcRequestBuilders.post("/").param("url", "http://sina8.com");
        ResultActions performTrue = mockMvc.perform(url1);
        performTrue.andExpect(MockMvcResultMatchers.status().is(200));
        ResultActions performTrue1 = mockMvc.perform(url1);
        String newRes = performTrue1.andReturn().getResponse().getContentAsString();
        JSONObject jsonObject = JSONUtil.parseObj(newRes);
        Assertions.assertEquals(0, ((JSONObject) jsonObject.get("data")).get("id"));
    }


    @Test
    @DisplayName("获取长链接")
    void getUrl() throws Exception {
        MockHttpServletRequestBuilder url1 = MockMvcRequestBuilders.post("/").param("url", "http://sina811.com");
        ResultActions performTrue = mockMvc.perform(url1);
        performTrue.andExpect(MockMvcResultMatchers.status().is(200));
        String newRes = performTrue.andReturn().getResponse().getContentAsString();
        JSONObject jsonObject = JSONUtil.parseObj(newRes);
        String shortUrl = (String) ((JSONObject) jsonObject.get("data")).get("shortUrl");
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get("/" + shortUrl);
        ResultActions perform = mockMvc.perform(mockHttpServletRequestBuilder);
        perform.andExpect(MockMvcResultMatchers.status().is(200));
    }

}
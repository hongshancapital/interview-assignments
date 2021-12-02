package com.zhangliang.suril.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.zhangliang.suril.controller.params.PostUrlParams;
import com.zhangliang.suril.controller.view.BaseResult;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ShortUrlControllerTest {

    @Value("${config.short-url-length}")
    private Integer shortUrlLength;

    @Value("${config.short-url-prefix}")
    private String prefix;

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("测试存储后，再用返回键取回")
    public void postUrlTest_returl_get() throws Exception {
        PostUrlParams postUrl = new PostUrlParams();
        postUrl.setOriginalUrl("http://www.123.com");
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(postUrl);

        MvcResult result = mvc.perform(post("/api/url").contentType(MediaType.APPLICATION_JSON).content(content))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        JSONObject jsonObject = JSONUtil.parseObj(response);
        String url = jsonObject.get("playLoad").toString();

        mvc.perform(get("/api/url?shortUrl=" + url))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.playLoad").value("http://www.123.com"));
    }

    @Test
    @DisplayName("测试存储")
    public void postUrlTest() throws Exception {
        PostUrlParams postUrl = new PostUrlParams();
        postUrl.setOriginalUrl("http://www.123.com");

        ObjectMapper mapper = new ObjectMapper();

        String content = mapper.writeValueAsString(postUrl);

        mvc.perform(post("/api/url").contentType(MediaType.APPLICATION_JSON).content(content))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("200"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.playLoad").value(Matchers.startsWith(prefix)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.playLoad")
                        .value(Matchers.hasLength(prefix.length() + shortUrlLength))).andDo((result) -> {

                });
    }

    @Test
    @DisplayName("测试空取回再用返回键取回")
    public void getUrlTest() throws Exception {
        mvc.perform(get("/api/url?shortUrl=http://123.com"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("200"));
    }


}

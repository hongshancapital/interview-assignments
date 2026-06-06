package com.heyenan.shorturldemo.webservice.controller;

import com.heyenan.shorturldemo.common.request.ShortUrlRequest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.annotation.Resource;

import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class ShortUrlControllerTest {

    @Resource
    private MockMvc mvc;
    private ShortUrlRequest request;

    @BeforeEach
    public void init() {
        request = new ShortUrlRequest();
    }

    @AfterEach
    public void clear() {
        request = null;
    }


    @DisplayName("hash策略操作空值")
    @SneakyThrows
    @Test
    void getShortUrl() throws Exception {

        ShortUrlRequest request = new ShortUrlRequest();
        request.setOriginUrl("");
        String requestJson = com.alibaba.fastjson.JSONObject.toJSONString(request);
        mvc.perform(MockMvcRequestBuilders.post("/genShortUrl").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().is2xxSuccessful())
        ;
    }

    @DisplayName("hash策略操作成功")
    @SneakyThrows
    @Test
    void getShortUrl2() throws Exception {

        ShortUrlRequest request = new ShortUrlRequest();
        request.setOriginUrl("https://github.com/scdt-china/interview-assignments/tree/master/java");
        String requestJson = com.alibaba.fastjson.JSONObject.toJSONString(request);
        mvc.perform(MockMvcRequestBuilders.post("/genShortUrl").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().is2xxSuccessful())
        ;
    }

    @DisplayName("hash策略操作输入格式错误")
    @SneakyThrows
    @Test
    void getShortUrl3() throws Exception {

        ShortUrlRequest request = new ShortUrlRequest();
        request.setOriginUrl("!!!^^%");
        String requestJson = com.alibaba.fastjson.JSONObject.toJSONString(request);
        mvc.perform(MockMvcRequestBuilders.post("/genShortUrl").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().is2xxSuccessful())
        ;
    }
    @DisplayName("hash策略操作开头http")
    @SneakyThrows
    @Test
    void getShortUrl4() throws Exception {
        ShortUrlRequest request = new ShortUrlRequest();
        request.setOriginUrl("github.com/scdt-china/interview-assignments/tree/master/java");
        String requestJson = com.alibaba.fastjson.JSONObject.toJSONString(request);
        mvc.perform(MockMvcRequestBuilders.post("/genShortUrl").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().is2xxSuccessful())
        ;
    }
    @DisplayName("id策略操作成功")
    @SneakyThrows
    @Test
    void getShortUrl5() throws Exception {
        ShortUrlRequest request = new ShortUrlRequest();
        request.setOriginUrl("https://github.com/scdt-china/interview-assignments/tree/master/java");
        String requestJson = com.alibaba.fastjson.JSONObject.toJSONString(request);
        mvc.perform(MockMvcRequestBuilders.post("/genShortUrl").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().is2xxSuccessful())
        ;
    }


}

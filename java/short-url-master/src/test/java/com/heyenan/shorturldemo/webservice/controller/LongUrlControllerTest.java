package com.heyenan.shorturldemo.webservice.controller;


import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.annotation.Resource;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class LongUrlControllerTest {
    @Resource
    private MockMvc mvc;


    @DisplayName("正确重定向短链接")
    @SneakyThrows
    @Test
    void getOriginUrl() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/getLongUrl/{shortUrl}", "1YZc4r"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("redirect:")));
    }

    @DisplayName("错误重定向短链接")
    @SneakyThrows
    @Test
    void getOriginUrlError() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/getLongUrl/{shortUrl}", "9AJ73oyWaF"))
                .andExpect(content().string(containsString("error/404")));
    }

    @DisplayName("空重定向短链接")
    @SneakyThrows
    @Test
    void getOriginUrlError2() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/getLongUrl/{shortUrl}", ""))
                .andExpect(content().string(containsString("")));
    }

}

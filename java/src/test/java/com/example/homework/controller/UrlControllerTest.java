package com.example.homework.controller;

import com.example.homework.HomeworkApplication;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

@Slf4j
@SpringBootTest(classes = HomeworkApplication.class)
class UrlControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    private HttpHeaders headers;
    public static final String baseUrl = "/url";

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        headers = new HttpHeaders();
    }

    @SneakyThrows
    @Test
    void getLongUrl() {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/getLong")
                        .param("shortUrl", "1")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        log.info(contentAsString);
    }

    @SneakyThrows    @Test
    void saveLongUrl() {
        String longUrl = "https://github.com/scdt-china/interview-assignments";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/saveLong")
                        .param("longUrl", longUrl)
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        log.info(contentAsString);
    }
}
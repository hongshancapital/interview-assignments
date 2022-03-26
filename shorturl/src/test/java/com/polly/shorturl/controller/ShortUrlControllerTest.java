package com.polly.shorturl.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author polly
 * @date 2022.03.23 12:05:15
 */
@AutoConfigureMockMvc
@SpringBootTest
public class ShortUrlControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void test01() throws Exception {
        String result = mvc.perform(MockMvcRequestBuilders.post("/insert")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED).param("url", "longurl"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        System.out.println(result);
    }

    @Test
    public void test02() throws Exception {
        String result = mvc.perform(MockMvcRequestBuilders.get("/get")
                        .param("url", "shorturl")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        System.out.println(result);
    }
}

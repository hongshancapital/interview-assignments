package com.example.tinyurl.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TinyurlControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void encode() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/tinyurl/encode")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void decode() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/tinyurl/decode")).andExpect(MockMvcResultMatchers.status().isOk());
    }
}
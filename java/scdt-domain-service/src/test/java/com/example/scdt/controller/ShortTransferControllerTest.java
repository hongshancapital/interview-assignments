package com.example.scdt.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * @author JonathanCheung
 * @date 2021/12/13 10:52
 * @describe
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ShortTransferControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void longUrlToShortUrlTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/url/longUrlToShortUrl?longUrl=https://www.baidu.com/img/PCfb_5bf082d29588c07f842ccde3f97243ea.png"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void shortUrlToLongUrlTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/url/shortUrlToLongUrl?shortUrl=Rf6Zj2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}

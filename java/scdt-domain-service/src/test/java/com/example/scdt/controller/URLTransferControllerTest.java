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

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class URLTransferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void longUrlToShortUrlTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/url/longUrlToShortUrl?longUrl=www.baidu" +
                        ".com/idaifaehhafd/iwjfihaie3482642/jdiajfiaejifewhihio.jpg"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void shortUrlToLongUrlTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/url/shortUrlToLongUrl?shortUrl=nq6V7j"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}

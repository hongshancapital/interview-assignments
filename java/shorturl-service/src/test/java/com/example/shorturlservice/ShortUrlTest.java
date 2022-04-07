package com.example.shorturlservice;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Created by xingxing.yu on 2022/3/22.
 */
@SpringBootTest(classes = ShorturlServiceApplication.class)
@AutoConfigureMockMvc
public class ShortUrlTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test001() throws Exception {
        MvcResult saveResult = mockMvc.perform(MockMvcRequestBuilders.get("/checkHealth"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String pingResultStr = saveResult.getResponse().getContentAsString();
        Assertions.assertTrue(pingResultStr.equals("success"), "success");
    }

}

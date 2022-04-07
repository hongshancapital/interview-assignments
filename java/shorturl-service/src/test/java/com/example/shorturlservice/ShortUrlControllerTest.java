package com.example.shorturlservice;

import com.example.shorturlservice.controller.ShortUrlController;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Created by xingxing.yu on 2022/3/22.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShortUrlControllerTest {
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ShortUrlController()).build();
    }

    @Test
    public void test001() throws Exception {
        MvcResult saveResult = mockMvc.perform(MockMvcRequestBuilders.get("/checkHealth"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String pingResultStr = saveResult.getResponse().getContentAsString();
        Assertions.assertTrue(pingResultStr.equals("success"), "success");
    }

}

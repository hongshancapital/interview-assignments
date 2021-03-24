package com.snail.shorturlservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MockTest {
    @Autowired
    protected MockMvc mockMvc;

    @Test
    public void testShorten() throws Exception {
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/surl/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"url\":\"https://www.jianshu.com/p/13408dd4bef7\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        System.out.println(result);
    }

    @Test
    public void testGet() throws Exception {
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/surl/ZUVoB7")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        System.out.println(result);
    }
}

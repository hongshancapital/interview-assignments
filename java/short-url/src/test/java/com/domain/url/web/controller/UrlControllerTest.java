package com.domain.url.web.controller;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UrlControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test1Shorten() throws Exception {
        String s = "{\n" +
                "  \"url\": \"https://image.baidu.com/user/logininfo?src=pc&page=searchresult&time=1637927056021\"\n" +
                "}";
        final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/url/shorten").content(s).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        Assert.assertNotNull(result);
    }

    @Test
    public void test2Original() throws Exception {
        final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/url/original?shortUrl=https://short.com/2Bi"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        Assert.assertNotNull(result);
    }
}
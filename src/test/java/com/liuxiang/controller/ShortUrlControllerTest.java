package com.liuxiang.controller;

import cn.hutool.core.lang.Assert;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * @author liuxiang6
 * @date 2022-01-07
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ShortUrlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGenerateShortUrl() throws Exception {
        MvcResult mvcResult = this.mockMvc
            .perform(MockMvcRequestBuilders.post("/long2short").param("longUrl", "http://www.baidu.com"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();

        Assert.isTrue(StringUtils.isNotBlank(mvcResult.getResponse().getContentAsString()));
    }

    @Test
    public void testGetLongUrl() throws Exception {
        //先put
        this.mockMvc
            .perform(MockMvcRequestBuilders.post("/long2short").param("longUrl", "http://www.baidu.com"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();

        MvcResult mvcResult = this.mockMvc
            .perform(MockMvcRequestBuilders.get("/short2long/bfa89e56"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();

        Assert.isTrue(mvcResult.getResponse().getContentAsString().contains("http://www.baidu.com"));
    }
}
package com.zm.demo;

import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.zm.demo.controller.UrlController;
import com.zm.demo.enums.ResultCodeEnum;

/**
 * @ClassName UrlControllerTest
 * @Author zhaomin
 * @Date 2021/10/11 9:59
 **/
@SpringBootTest
@AutoConfigureMockMvc
public class UrlControllerTest {

    @Autowired
    private UrlController urlController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testOK() throws Exception {
        String longUrl= "www.baidu.com/abc/123";

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/url/shortUrl?longUrl=" + longUrl))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString(Charsets.UTF_8);

        ObjectMapper objectMapper = new ObjectMapper();
        Map resultMap = objectMapper.readValue(result, Map.class);
        Object shortUrl = resultMap.get("data");

        this.mockMvc.perform(MockMvcRequestBuilders.get("/url/longUrl?shortUrl=" + shortUrl))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value(longUrl));
    }


    @Test
    public void testParamEmpty() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/url/shortUrl" ))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ResultCodeEnum.PARAM_EMPTY.getCode()));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/url/shortUrl?longUrl=" ))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ResultCodeEnum.PARAM_EMPTY.getCode()));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/url/longUrl" ))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ResultCodeEnum.PARAM_EMPTY.getCode()));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/url/longUrl?shortUrl=" ))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ResultCodeEnum.PARAM_EMPTY.getCode()));
    }


    @Test
    public void testExistShortUrl() throws Exception {
        String preShortUrl = null;

        for (int i = 0; i < 2; i++) {
            String longUrl= "www.baidu.com/abc/456";

            MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/url/shortUrl?longUrl=" + longUrl))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            String result = mvcResult.getResponse().getContentAsString(Charsets.UTF_8);

            ObjectMapper objectMapper = new ObjectMapper();
            Map resultMap = objectMapper.readValue(result, Map.class);
            String shortUrl = (String) resultMap.get("data");

            if (i > 0) {
                Assertions.assertEquals(preShortUrl, shortUrl);
            }

            preShortUrl = shortUrl;
        }
    }


    @Test
    public void testWrongDomain() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/url/longUrl?shortUrl=www.aaa.com/12345"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    }

}

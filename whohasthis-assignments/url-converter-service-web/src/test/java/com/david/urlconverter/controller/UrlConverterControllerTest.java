package com.david.urlconverter.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
public class UrlConverterControllerTest {

    private String longUrl = "https://www.baidu.com/23389dlw?320d-3=093id";

    private String invalidLongUrl = "http://173.3233.330.93:8390/938293";

    //根据实际请求的返回数据设置
    private String shortUrl = "qn======";

    private String invalidShortUrl1="c1F=d===";

    private String invalidShortUrl2="99999999";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @BeforeEach
    public void beforeTest(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .defaultResponseCharacterEncoding(Charset.forName("UTF-8"))
                .build();
    }

    @Test
    public void testTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/url/test")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).isEqualTo("{\"code\":\"0\",\"msg\":\"ok\"}");
    }

    @Test
    public void testGenerateShortUrl() throws Exception {
        //normal request
        MvcResult result = mockMvc.perform(get("/url/short/generate")
                .accept(MediaType.APPLICATION_JSON)
                .param("longUrl",longUrl))
                .andReturn();
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).contains("{\"code\":\"0\",\"msg\":\"ok\"");


        //empty param
        result = mockMvc.perform(get("/url/short/generate")
                .accept(MediaType.APPLICATION_JSON)
                .param("longUrl","  "))
                .andReturn();
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).contains("{\"code\":\"7001\",\"msg\":\"请求参数为空\"");

        //invalid param
        result = mockMvc.perform(get("/url/short/generate")
                .accept(MediaType.APPLICATION_JSON)
                .param("longUrl",invalidLongUrl))
                .andReturn();
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).contains("{\"code\":\"7002\",\"msg\":\"请求参数格式错误\"");

    }

    @Test
    public void testRetrieveLongUrl() throws Exception {
        //normal request
        MvcResult result = mockMvc.perform(get("/url/long/retrieve")
                .accept(MediaType.APPLICATION_JSON)
                .param("shortUrl",shortUrl))
                .andReturn();
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).contains("{\"code\":\"0\",\"msg\":\"ok\"");


        //empty param
        result = mockMvc.perform(get("/url/long/retrieve")
                .accept(MediaType.APPLICATION_JSON)
                .param("shortUrl","  "))
                .andReturn();
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).contains("{\"code\":\"7001\",\"msg\":\"请求参数为空\"");

        //invalid param
        result = mockMvc.perform(get("/url/long/retrieve")
                .accept(MediaType.APPLICATION_JSON)
                .param("shortUrl",invalidShortUrl1))
                .andReturn();
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).contains("{\"code\":\"7002\",\"msg\":\"请求参数格式错误\"");


        //invalid param
        result = mockMvc.perform(get("/url/long/retrieve")
                .accept(MediaType.APPLICATION_JSON)
                .param("shortUrl",invalidShortUrl2))
                .andReturn();
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).contains("{\"code\":\"7003\",\"msg\":\"短域名无效\"");
    }
}


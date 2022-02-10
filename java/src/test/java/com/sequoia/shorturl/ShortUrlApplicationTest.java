package com.sequoia.shorturl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
class ShortUrlApplicationTest {

    @BeforeAll
    static void init() {

    }

    @Autowired
    private MockMvc mockMvc;

    void invoke(String url, String paramName, String value) throws Exception {
        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders.get(url)
                .param(paramName, value)
                .accept(MediaType.TEXT_HTML_VALUE)
                .contentType(MediaType.TEXT_HTML_VALUE)).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    @Order(1)
    public void getShortUrl() throws Exception {
        String longUrl = "http://www.xbd.com/product/1234567";
        invoke("/shortUrlApi/getShortUrl", "longUrl", longUrl);
        invoke("/shortUrlApi/getShortUrl", "longUrl", null);

        invoke("/shortUrlApi/getShortUrl", "longUrl", "1234");
        invoke("/shortUrlApi/getShortUrl", "longUrl", "http://www.xbd.com/product/123456789");
    }

    @Test
    @Order(2)
    public void getLongUrl() throws Exception{
        String shortUrl = "http://a.cn/ALuaxFc1";
        invoke("/shortUrlApi/getLongUrl", "shortUrl", shortUrl);
        invoke("/shortUrlApi/getLongUrl", "shortUrl", "");
    }

}



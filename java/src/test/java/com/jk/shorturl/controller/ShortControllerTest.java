package com.jk.shorturl.controller;


import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Slf4j
class ShortControllerTest {

    @Autowired

    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        log.debug("i am in BeforeEach setUP");
    }

    @AfterEach
    void tearDown() {
        log.debug("i am in AfterEach setUP");
    }

    @Test
    void testAll() {
        getShortUrl("");
        getShortUrl("http://wwww.abc.com/");
        String shortURL = getShortUrl("http://wwww.abc.com/");
        try {
            JSONObject jsonObject = new JSONObject(shortURL);
            getLongUrl(null);
            getLongUrl(jsonObject.get("message").toString());
            getLongUrl("http://t.com/wqqqqq");
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    String getShortUrl(String url) {

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get("/api/short/short");

        String responseString = null;
        try {
            responseString = mockMvc.perform(MockMvcRequestBuilders.post("/api/short/short")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("longURL", url)  //数据的格式   .contentType(MediaType.APPLICATION_FORM_URLENCODED)   数据的格式请求的url,请求的方法是get.contentType(MediaType.APPLICATION_FORM_URLENCODED)  //数据的格式 .param("pcode","root")         //添加参数
            ).andExpect(status().isOk())    //返回的状态是200
                    .andDo(print())    //打印出请求和相应的内容
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.debug("i am in getShortUrl setUP 返回值={}", responseString); //在Controller 中加 @ResponseBody 可输出要返回的内容
        return responseString;
    }

    void getLongUrl(String url) {

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get("/api/short/long");
        mockHttpServletRequestBuilder.param("shortURL", url); //要传入的参数
        ResultActions resultActions = null;
        try {
            resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
            resultActions.andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.debug("i am in getLongUrl setUP");

    }
}

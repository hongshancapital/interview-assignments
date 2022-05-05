package com.url.controller;

import com.url.Application;
import com.url.bean.UrlResultBean;
import com.url.service.UrlTransService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * 短域名生成转化测试类
 * @ClassName UrlTransControllerTests
 * @Author jeckzeng
 * @Date 2022/4/30
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Slf4j
@AutoConfigureMockMvc
public class UrlTransControllerTests {

    @Autowired
    private MockMvc mock;
    @Autowired
    private UrlTransService urlTransService;

    @Test
    public void getShortUrlTest() throws Exception{
        String longUrl = "https://www.baidu.com/test";
        MvcResult mvcResult = mock.perform(
                MockMvcRequestBuilders.post("/url/short")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("UTF-8")
                        .param("longUrl",longUrl)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("200")) //比较json结果值
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        response.setCharacterEncoding("UTF-8");
        String content = response.getContentAsString();
        log.info("short Result: {}", content);
    }

    @Test
    public void getShortUrlParameterEmptyTest() throws Exception{
        String longUrl = "";
        MvcResult mvcResult = mock.perform(
                        MockMvcRequestBuilders.post("/url/short")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .characterEncoding("UTF-8")
                                .param("longUrl",longUrl)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("400")) //比较json结果值
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        response.setCharacterEncoding("UTF-8");
        String content = response.getContentAsString();
        log.info("short Result: {}", content);
    }

    @Test
    public void getShortUrlParameterCheckErrorTest() throws Exception{
        String longUrl = "1234sfd**";
        MvcResult mvcResult = mock.perform(
                        MockMvcRequestBuilders.post("/url/short")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .characterEncoding("UTF-8")
                                .param("longUrl",longUrl)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("400")) //比较json结果值
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        response.setCharacterEncoding("UTF-8");
        String content = response.getContentAsString();
        log.info("short Result: {}", content);
    }

    @Test
    public void getShortUrlServiceErrorTest() throws Exception{
        String longUrl = "service_error_test";
        MvcResult mvcResult = mock.perform(
                        MockMvcRequestBuilders.post("/url/short")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .characterEncoding("UTF-8")
                                .param("longUrl",longUrl)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("500")) //比较json结果值
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        response.setCharacterEncoding("UTF-8");
        String content = response.getContentAsString();
        log.info("short Result: {}", content);
    }

    @Test
    public void getLongUrlErrorTest() throws Exception{
        String shortUrl = "http://t.cn/0";
        MvcResult mvcResult = mock.perform(
                        MockMvcRequestBuilders.post("/url/long")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .characterEncoding("UTF-8")
                                .param("shortUrl",shortUrl)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("200")) //比较json结果值
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        response.setCharacterEncoding("UTF-8");
        String content = response.getContentAsString();
        log.info("long Result: {}", content);
    }

    @Test
    public void getLongUrlSuccessTest() throws Exception{
        String longUrl = "https://www.baidu.com/test";
        UrlResultBean urlResultBean = urlTransService.getShortUrl(longUrl);
        MvcResult mvcResult = mock.perform(
                        MockMvcRequestBuilders.post("/url/long")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .characterEncoding("UTF-8")
                                .param("shortUrl",urlResultBean.getUrlData())
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("200")) //比较json结果值
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        response.setCharacterEncoding("UTF-8");
        String content = response.getContentAsString();
        log.info("long Result: {}", content);
    }

    @Test
    public void getLongUrlParameterEmptyTest() throws Exception{
        String longUrl = "";
        UrlResultBean urlResultBean = urlTransService.getShortUrl(longUrl);
        MvcResult mvcResult = mock.perform(
                        MockMvcRequestBuilders.post("/url/long")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .characterEncoding("UTF-8")
                                .param("shortUrl",urlResultBean.getUrlData())
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("400")) //比较json结果值
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        response.setCharacterEncoding("UTF-8");
        String content = response.getContentAsString();
        log.info("long Result: {}", content);
    }

    @Test
    public void getLongUrlNotExistsTest() throws Exception{
        String shortUrl = "https://t.cn/qwerq";
        MvcResult mvcResult = mock.perform(
                        MockMvcRequestBuilders.post("/url/long")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .characterEncoding("UTF-8")
                                .param("shortUrl",shortUrl)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("600")) //比较json结果值
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        response.setCharacterEncoding("UTF-8");
        String content = response.getContentAsString();
        log.info("long Result: {}", content);
    }

    @Test
    public void getLongUrlServiceErrorTest() throws Exception{
        String shortUrl = "service_error_test";
        MvcResult mvcResult = mock.perform(
                        MockMvcRequestBuilders.post("/url/long")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .characterEncoding("UTF-8")
                                .param("shortUrl",shortUrl)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("500")) //比较json结果值
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        response.setCharacterEncoding("UTF-8");
        String content = response.getContentAsString();
        log.info("long Result: {}", content);
    }

    @Test
    public void createResTest(){
        UrlResultBean urlResultBean = new UrlResultBean();
        urlResultBean.setCode(200);
        urlResultBean.setDesc("success!");
        log.info("result :{}", urlResultBean.toString());
    }

}
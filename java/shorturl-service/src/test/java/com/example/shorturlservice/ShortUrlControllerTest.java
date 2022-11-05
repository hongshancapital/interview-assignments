package com.example.shorturlservice;

import com.alibaba.fastjson.JSON;
import com.example.shorturlservice.domain.QueryShortUrlRequest;
import com.example.shorturlservice.domain.SaveLongUrlRequest;
import com.example.shorturlservice.service.ShortUrlGenerator;
import com.example.shorturlservice.service.ShortUrlService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

/**
 * @Description 短域名单测
 * @Author xingxing.yu
 * @Date 2022/04/15 17:49
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShortUrlControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private ShortUrlService shortUrlService;


    @Resource
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testCheckHealth() throws Exception {
        MvcResult saveResult = mockMvc.perform(MockMvcRequestBuilders.get("/checkHealth"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String pingResultStr = saveResult.getResponse().getContentAsString();
        Assertions.assertTrue(pingResultStr.equals("success"), "success");
    }

    @Test
    public void testSaveLongUrlNormal() throws Exception {
        SaveLongUrlRequest request = new SaveLongUrlRequest();
        request.setLongUrl("www.ctrip.com");
        String longUrl = JSON.toJSONString(request);
        MvcResult saveResult = mockMvc.perform(MockMvcRequestBuilders.post("/saveLongUrl").contentType(MediaType.APPLICATION_JSON_VALUE).content(longUrl))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String resStr = saveResult.getResponse().getContentAsString();
        Assertions.assertNotNull(resStr);
    }

    @Test
    public void testSaveLongUrlIdempotent() throws Exception {
        String url = shortUrlService.saveLongUrl("www.ctrip.com");

        SaveLongUrlRequest request = new SaveLongUrlRequest();
        request.setLongUrl("www.ctrip.com");
        String longUrl = JSON.toJSONString(request);
        MvcResult saveResult = mockMvc.perform(MockMvcRequestBuilders.post("/saveLongUrl").contentType(MediaType.APPLICATION_JSON_VALUE).content(longUrl))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String resStr = saveResult.getResponse().getContentAsString();
        Assertions.assertNotNull(resStr);
    }

    @Test
    public void testSaveLongUrlCollision() throws Exception {
        String[] urlRes = ShortUrlGenerator.shortUrl("www.ctrip.com");


        SaveLongUrlRequest request = new SaveLongUrlRequest();
        request.setLongUrl("www.ctrip.com");
        String longUrl = JSON.toJSONString(request);
        MvcResult saveResult = mockMvc.perform(MockMvcRequestBuilders.post("/saveLongUrl").contentType(MediaType.APPLICATION_JSON_VALUE).content(longUrl))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String resStr = saveResult.getResponse().getContentAsString();
        Assertions.assertNotNull(resStr);
    }

    @Test
    public void testSaveLongUrlNull() throws Exception {
        SaveLongUrlRequest request = new SaveLongUrlRequest();
        request.setLongUrl("");
        String longUrl = JSON.toJSONString(request);
        MvcResult saveResult = mockMvc.perform(MockMvcRequestBuilders.post("/saveLongUrl").contentType(MediaType.APPLICATION_JSON_VALUE).content(longUrl))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String resStr = saveResult.getResponse().getContentAsString();
        Assertions.assertNotNull(resStr);
    }

    @Test
    public void testSaveLongUrlLimit() throws Exception {
        SaveLongUrlRequest request = new SaveLongUrlRequest();
        request.setLongUrl("www.ctrip.comwww.ctrip.comwww.ctrip.comwww.ctrip.comwww.ctrip.comwww.ctrip.comwww.ctrip.comwww.ctrip.comwww.ctrip.comwww.ctrip.com");
        String longUrl = JSON.toJSONString(request);
        MvcResult saveResult = mockMvc.perform(MockMvcRequestBuilders.post("/saveLongUrl").contentType(MediaType.APPLICATION_JSON_VALUE).content(longUrl))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String resStr = saveResult.getResponse().getContentAsString();
        Assertions.assertNotNull(resStr);
    }

    @Test
    public void testGetLongUrlNormal() throws Exception {
        String url = shortUrlService.saveLongUrl("www.ctrip.com");

        QueryShortUrlRequest request = new QueryShortUrlRequest();
        request.setShortUrl(url);
        String shortUrl = JSON.toJSONString(request);
        MvcResult saveResult = mockMvc.perform(MockMvcRequestBuilders.post("/getShortUrl").contentType(MediaType.APPLICATION_JSON_VALUE).content(shortUrl))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String resStr = saveResult.getResponse().getContentAsString();
        Assertions.assertNotNull(resStr);
    }

    @Test
    public void testGetLongUrlNull() throws Exception {
        QueryShortUrlRequest request = new QueryShortUrlRequest();
        request.setShortUrl("xxyuMzfp");
        String shortUrl = JSON.toJSONString(request);
        MvcResult saveResult = mockMvc.perform(MockMvcRequestBuilders.post("/getShortUrl").contentType(MediaType.APPLICATION_JSON_VALUE).content(shortUrl))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String resStr = saveResult.getResponse().getContentAsString();
        Assertions.assertNotNull(resStr);
    }

}

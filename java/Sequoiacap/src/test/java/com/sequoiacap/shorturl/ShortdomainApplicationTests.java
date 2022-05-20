package com.sequoiacap.shorturl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sequoiacap.shorturl.controller.IndexController;
import com.sequoiacap.shorturl.service.UrlService;

import com.sequoiacap.shorturl.utils.BaseEncoder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.UnsupportedEncodingException;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ShortdomainApplicationTests {

    @Autowired
    protected UrlService urlService;

    @Autowired
    private IndexController indexController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
    }

    @Test
    public void testAShortUrl() {
        String key = urlService.getShortUrl("http://www.a.com/path/page.html");
        Assert.assertEquals("0", key);
    }

    @Test
    public void testBUrl() {
        String url = urlService.getUrl("0");
        Assert.assertEquals("http://www.a.com/path/page.html", url);
    }

    @Test
    public void testCEncoder(){
        String value = BaseEncoder.encode(62);
        Assert.assertEquals("10", value);
    }

    @Test
    public void testDRequestShortUrl() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/shorturl/get_short_url").param("url","http://www.900.com/page"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        JSONObject json = JSON.parseObject(body);
        Assert.assertEquals(100, json.getIntValue("result"));
    }
    @Test
    public void testDRequestShortUrlErrParam() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/shorturl/get_short_url").param("url",""))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        JSONObject json = JSON.parseObject(body);
        Assert.assertEquals(102, json.getIntValue("result"));
    }

    @Test
    public void testERequestUrl() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/shorturl/get_url").param("key","1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        JSONObject json = JSON.parseObject(body);
        Assert.assertEquals(100, json.getIntValue("result"));
    }

    @Test
    public void testERequestUrlNoData() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/shorturl/get_url").param("key","10"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        JSONObject json = JSON.parseObject(body);
        Assert.assertEquals(104, json.getIntValue("result"));
    }

    @Test
    public void testERequestUrlErrParam() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/shorturl/get_url").param("key",""))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        JSONObject json = JSON.parseObject(body);
        Assert.assertEquals(102, json.getIntValue("result"));
    }

}

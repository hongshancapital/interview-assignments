package com.zs.shorturl.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zs.shorturl.ShorturlApplication;
import com.zs.shorturl.cache.LongOrShortUrlCache;
import com.zs.shorturl.enity.vo.Result;
import com.zs.shorturl.utils.ShortUrlGenerateUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShorturlApplication.class)
public class ShrotUrlControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    public MockMvc mockMvc;

    public ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setupMockMvc(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        LongOrShortUrlCache.putShortForKey("http://www.zs.com/W4QQQhdv","https://cn.bing.com/search?q=idea+%E8%87%AA%E5%8A%A8%E5%88%A0%E9%99%A4%E6%97%A0%E6%95%88import&form=ANNNB1&refig=6b8f6dd6439240228307b79cfeba39ec");
    }

    @Test
    public void getShortUrl() throws Exception {

        String responseString = mockMvc.perform(
                get("/api/shorturl")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("url","https://cn.bing.com/search?q=idea+%E8%87%AA%E5%8A%A8%E5%88%A0%E9%99%A4%E6%97%A0%E6%95%88import&form=ANNNB1&refig=6b8f6dd6439240228307b79cfeba39ec")
        ).andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Result result = objectMapper.readValue(responseString, Result.class);
        String data = (String)result.getData();
        Assert.assertTrue(data.startsWith(ShortUrlGenerateUtil.SHORT_URL_PRE));
        String serialCode = data.substring(data.lastIndexOf("/") + 1);
        Assert.assertEquals(8,serialCode.length());
        Assert.assertEquals('W',serialCode.charAt(0));

    }

    @Test
    public void getLongUrl() throws Exception {
        String responseString = mockMvc.perform(
                get("/api/longurl")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("url","http://www.zs.com/W4QQQhdv")
        ).andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        Result result = objectMapper.readValue(responseString, Result.class);
        Assert.assertEquals("https://cn.bing.com/search?q=idea+%E8%87%AA%E5%8A%A8%E5%88%A0%E9%99%A4%E6%97%A0%E6%95%88import&form=ANNNB1&refig=6b8f6dd6439240228307b79cfeba39ec",result.getData());

    }
}
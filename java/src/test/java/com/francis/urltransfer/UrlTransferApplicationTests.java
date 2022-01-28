package com.francis.urltransfer;

import com.alibaba.fastjson.JSONObject;
import com.francis.urltransfer.common.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.*;

@SpringBootTest
@AutoConfigureMockMvc
class UrlTransferApplicationTests {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void test() throws Exception {
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.get("/test/ok")).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String content1 = mvcResult1.getResponse().getContentAsString();
        System.out.println(content1);

        MvcResult mvcResult2 = mockMvc.perform(MockMvcRequestBuilders.get("/test/warning")).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String content2 = mvcResult2.getResponse().getContentAsString();
        System.out.println(content2);
    }

    /**
     * 正常存储短域名
     *
     * @throws Exception
     */
    @Test
    public void testAddShortUrl() throws Exception {
        String longUrl = "https://cn.bing.com/";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/shorter/add").queryParam("url", longUrl)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String content = response.getContentAsString();
        Result result = JSONObject.parseObject(content, Result.class);
        assertNotNull(result.getData());
    }

    /**
     * 多次存储相同的短域名
     *
     * @throws Exception
     */
    @Test
    public void testAddShortUrlOnceMoreTimes() throws Exception {
        String longUrl = "https://cn.bing.com/";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/shorter/add").queryParam("url", longUrl)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Result<String> result = JSONObject.parseObject(mvcResult.getResponse().getContentAsString(), Result.class);
        String shortUrl = result.getData();
        for (int i = 0; i < 100; ++i) {
            MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.get("/shorter/add").queryParam("url", longUrl)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
            Result<String> result1 = JSONObject.parseObject(mvcResult1.getResponse().getContentAsString(), Result.class);
            System.out.println(result1);
            assertEquals(shortUrl, result1.getData());
        }
    }

    /**
     * 存储多个不同的短域名
     *
     * @throws Exception
     */
    @Test
    public void testAddMoreShortUrl() throws Exception {
        for (int i = 0; i < 10000; ++i) {
            String longUrl = "https://cn.bing.com/" + i;
            MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.get("/shorter/add").queryParam("url", longUrl)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
            MvcResult mvcResult2 = mockMvc.perform(MockMvcRequestBuilders.get("/shorter/add").queryParam("url", longUrl)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
            String result1 = mvcResult1.getResponse().getContentAsString();
            String result2 = mvcResult2.getResponse().getContentAsString();
            assertEquals(result1, result2);
        }
    }

    /**
     * 获取长域名
     *
     * @throws Exception
     */
    @Test
    public void testGetUrl() throws Exception {
        String longUrl = "https://cn.bing.com/";
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.get("/shorter/add").queryParam("url", longUrl)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Result<String> result1 = JSONObject.parseObject(mvcResult1.getResponse().getContentAsString(), Result.class);
        String shortUrl = result1.getData();
        MvcResult mvcResult2 = mockMvc.perform(MockMvcRequestBuilders.get("/shorter/get").queryParam("shortUrl", shortUrl)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Result<String> result2 = JSONObject.parseObject(mvcResult2.getResponse().getContentAsString(), Result.class);
        assertEquals(longUrl, result2.getData());
    }

    /**
     * 获取不存在的长域名
     *
     * @throws Exception
     */
    @Test
    public void testGetNotExistUrl() throws Exception {
        String shortUrl = "https://cn.bing.com/";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/shorter/get").queryParam("shortUrl", shortUrl)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Result<String> result = JSONObject.parseObject(mvcResult.getResponse().getContentAsString(), Result.class);
        assertEquals(result.getCode(), 404);
        assertNull(result.getData());
    }

    /**
     * 系统错误
     */
    @Test
    public void testSystemError() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/shorter/get")).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Result<String> result = JSONObject.parseObject(mvcResult.getResponse().getContentAsString(), Result.class);
        assertEquals(result.getCode(), 500);
        assertNull(result.getData());
    }

}

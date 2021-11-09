package com.nbasoccer.shorturl;

import com.alibaba.fastjson.JSONObject;
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
public class ShortUrlControllerTests {

    @Autowired
    private MockMvc mockMvc;

    /**
     *  生成短链接，压入缓存
     */
    @Test
    public void testGetShortUrlNoCache() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/create")
                        .param("longUrl","https://mvnrepository.com/artifact/org.jacoco/org.jacoco.core")
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     *  缓存中存在命中，返回对应短链接
     */
    @Test
    public void testGetShortUrlInCache() throws Exception {
        for (int i = 0; i < 2; i++) {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/create")
                            .param("longUrl","https://mvnrepository.com/artifact/org.jacoco/org.jacoco.core")
                            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print());
        }
    }

    /**
     * 查找原链接，未命中。
     * @throws Exception
     */
    @Test
    public void testGetOriginUrlNoCache() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/query")
                        .param("shortUrl","http://t.cn/1L1QY1ag")
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 查找原链接，命中返回
     * @throws Exception
     */
    @Test
    public void testGetOriginUrlInCache() throws Exception {
        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders.get("/api/create")
                        .param("longUrl","https://mvnrepository.com/artifact/org.jacoco/org.jacoco.core")
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        //先压入一条测试数据，来测试缓存命中能查找到原链接的情况
        JSONObject resultJSONObject = JSONObject.parseObject(result.getResponse().getContentAsString().replace("result:", ""));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/query")
                            .param("shortUrl",resultJSONObject.getJSONObject("data").getString("shortUrl"))
                            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 测试缓存淘汰
     * @throws Exception
     */
    @Test
    public void testEliminateCache() throws Exception {
        //超过1024初始值，将进行最少使用算法淘汰
        for (int i = 0; i < 1030; i++) {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/create")
                            .param("longUrl","https://mvnrepository.com/artifact/org.jacoco/org.jacoco.core" + i)
                            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print());
        }
    }

}

package com.xiaoxi666.tinyurl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaoxi666.tinyurl.domain.Response;
import com.xiaoxi666.tinyurl.domain.StoreParam;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.Charset;

import static com.xiaoxi666.tinyurl.controller.TinyUrlController.TINY_URL_PREFIX;

/**
 * @Author: xiaoxi666
 * @Date: 2022/4/17
 * @Version: 1.0
 * @Description: 直接从接口层进行测试，其实这种测试有点偏向于集成测试而非单元测试了
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class TinyUrlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    @DisplayName("正常情况")
    class NormalScene {
        private static final String LONG_URL = "https://JvHCVtEQp/HVJNIZGFx7GeMv6dIl";
        StoreParam storeParam = new StoreParam(LONG_URL, 0);

        @Test
        void storeAndFetchTest() throws Exception {
            // 长连接生成短链接
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/store")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(storeParam)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();
            String contentAsString = mvcResult.getResponse().getContentAsString();
            Response response = objectMapper.readValue(contentAsString, Response.class);
            String tinyUrl = (String) response.getData();

            // 获取短链接对应的长连接
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/fetch?tinyurl=" + tinyUrl)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();
            contentAsString = mvcResult.getResponse().getContentAsString();
            response = objectMapper.readValue(contentAsString, Response.class);
            String longUrl = (String) response.getData();
            Assertions.assertEquals(LONG_URL, longUrl);
        }
    }

    @Nested
    @DisplayName("异常情况")
    class ExceptionScene {

        @Test
        @DisplayName("无效入参[长连接不能为空]")
        void storeTest_emptyLongUrl() throws Exception {
            String longUrl = "";
            StoreParam storeParam = new StoreParam(longUrl, 0);

            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/store")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(storeParam)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();
            String contentAsString = mvcResult.getResponse().getContentAsString(Charset.defaultCharset());
            Response response = objectMapper.readValue(contentAsString, Response.class);
            Assertions.assertEquals(10001, response.getCode());
            Assertions.assertEquals(null, response.getData());
            Assertions.assertEquals("无效入参[长连接不能为空]", response.getMsg());
        }

        @Test
        @DisplayName("不允许的操作[禁止为本域名网址生成短链接]")
        void storeTest_notAllowedOperation() throws Exception {
            String longUrl = "https://xiaoxi666/tinyurl/JvHCVtEQp/HVJNIZGFx7GeMv6dIl";
            StoreParam storeParam = new StoreParam(longUrl, 0);

            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/store")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(storeParam)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();
            String contentAsString = mvcResult.getResponse().getContentAsString(Charset.defaultCharset());
            Response response = objectMapper.readValue(contentAsString, Response.class);
            Assertions.assertEquals(10003, response.getCode());
            Assertions.assertEquals(null, response.getData());
            Assertions.assertEquals("不允许的操作[禁止为本域名网址生成短链接]", response.getMsg());
        }

        @Test
        @DisplayName("无效入参[短连接不能为空]")
        void fetchTest_emptyTinyurl() throws Exception {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/fetch?tinyurl=")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();
            String contentAsString = mvcResult.getResponse().getContentAsString(Charset.defaultCharset());
            Response response = objectMapper.readValue(contentAsString, Response.class);
            Assertions.assertEquals(10001, response.getCode());
            Assertions.assertEquals(null, response.getData());
            Assertions.assertEquals("无效入参[短连接不能为空]", response.getMsg());
        }

        @Test
        @DisplayName("无效入参[非本平台的短链接]")
        void fetchTest_misMatchPrefix() throws Exception {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/fetch?tinyurl=" + "https://baidu.com/abc")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();
            String contentAsString = mvcResult.getResponse().getContentAsString(Charset.defaultCharset());
            Response response = objectMapper.readValue(contentAsString, Response.class);
            Assertions.assertEquals(10001, response.getCode());
            Assertions.assertEquals(null, response.getData());
            Assertions.assertEquals("无效入参[非本平台的短链接]", response.getMsg());
        }

        @Test
        @DisplayName("未找到[可能的原因：未生成/已过期/因内存限制被清理]")
        void fetchTest_notFound() throws Exception {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/fetch?tinyurl=" + TINY_URL_PREFIX + "abc")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();
            String contentAsString = mvcResult.getResponse().getContentAsString(Charset.defaultCharset());
            Response response = objectMapper.readValue(contentAsString, Response.class);
            Assertions.assertEquals(10004, response.getCode());
            Assertions.assertEquals(null, response.getData());
            Assertions.assertEquals("未找到[可能的原因：未生成/已过期/因内存限制被清理]", response.getMsg());
        }
    }

}
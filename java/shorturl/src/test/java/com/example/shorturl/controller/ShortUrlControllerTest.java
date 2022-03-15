package com.example.shorturl.controller;

import com.example.shorturl.bean.ResponseDTO;
import com.example.shorturl.manager.ShortUrlManager;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.Assert;

import java.util.Objects;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author yingchen
 * @date 2022/3/15
 */
@SpringBootTest
@AutoConfigureMockMvc
class ShortUrlControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void getShortUrlAndGetShortUrl() throws Exception {
        String originalUrl = "https://www.baidu.com";
        String shortUrl = "";
        // 获取短域名
        final MvcResult mvcResult = mockMvc
                .perform(
                        post("/getShortUrl")
                                .content("{\"originalUrl\":\"" + originalUrl + "\"}")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        final ResponseDTO<String> responseDTO = new Gson().fromJson(mvcResult.getResponse().getContentAsString(), new ParameterizedTypeReference<ResponseDTO<String>>() {
        }.getType());
        Assert.isTrue(responseDTO.getCode() == 0, "code is not 0");
        shortUrl = responseDTO.getData();

        // 获取短域名对应的原始域名
        final MvcResult mvcResult2 = mockMvc
                .perform(
                        post("/getOriginalUrl")
                                .content("{\"shortUrl\":\"" + shortUrl + "\"}")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        final ResponseDTO<String> responseDTO2 = new Gson().fromJson(mvcResult2.getResponse().getContentAsString(), new ParameterizedTypeReference<ResponseDTO<String>>() {
        }.getType());
        Assert.isTrue(responseDTO2.getCode() == 0, "code is not 0");
        // 判断原始域名是否正确
        Assert.isTrue(Objects.equals(responseDTO2.getData(), originalUrl), "originalUrl not match");
    }

    @Test
    public void testErrorParam() throws Exception {
        // 测试入参为空的场景
        final MvcResult mvcResult = mockMvc
                .perform(
                        post("/getShortUrl")
                                .content("{}")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        final ResponseDTO<String> responseDTO = new Gson().fromJson(mvcResult.getResponse().getContentAsString(), new ParameterizedTypeReference<ResponseDTO<String>>() {
        }.getType());
        Assert.isTrue(responseDTO.getCode() != 0, "error code is 0");


        // 测试入参为空的场景
        final MvcResult mvcResult2 = mockMvc
                .perform(
                        post("/getOriginalUrl")
                                .content("{}")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        final ResponseDTO<String> responseDTO2 = new Gson().fromJson(mvcResult2.getResponse().getContentAsString(), new ParameterizedTypeReference<ResponseDTO<String>>() {
        }.getType());
        Assert.isTrue(responseDTO2.getCode() != 0, "error code is 0");

        // 测试入参为错误值的场景
        final MvcResult mvcResult3 = mockMvc
                .perform(
                        post("/getOriginalUrl")
                                .content("{\"shortUrl\":\"!@$#^@\"}")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        final ResponseDTO<String> responseDTO3 = new Gson().fromJson(mvcResult3.getResponse().getContentAsString(), new ParameterizedTypeReference<ResponseDTO<String>>() {
        }.getType());
        Assert.isTrue(responseDTO3.getCode() != 0, "error code is 0");
    }
}
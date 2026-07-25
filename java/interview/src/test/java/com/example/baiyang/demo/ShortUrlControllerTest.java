package com.example.baiyang.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.baiyang.demo.constant.ParamErrorCode;
import com.example.baiyang.demo.constant.SystemErrorCode;
import com.example.baiyang.demo.model.RequestDTO;
import com.example.baiyang.demo.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author: baiyang.xdq
 * @date: 2021/12/17
 * @description:短域名服务controller类单元测试
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class ShortUrlControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    /**
     * 模拟http请求
     *
     * @param relativeUrl
     * @param requestDTO
     * @return
     */
    public Result mockHttpRequest(String relativeUrl, RequestDTO requestDTO) {
        try {
            MvcResult mvcResult = mockMvc.perform(post(relativeUrl)
                    .accept(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JSON.toJSONString(requestDTO)))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            String response = mvcResult.getResponse().getContentAsString();
            Result result = JSONObject.parseObject(response, Result.class);

            return result;

        } catch (Exception ex) {
            log.error("ShortUrlControllerTest.testGetLongUrl encounters an exception", ex.getMessage(), ex);
            return null;
        }
    }

    @Test
    public void testGetLongUrl() {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setShortUrl("Zveqyq");

        Result result = mockHttpRequest("/api/getLongUrl", requestDTO);

        Assert.assertEquals("获取长域名结果有误", "http://www.sequoiacap.cn/china/people/introduction/", null == result.getResponse() ? "" : result.getResponse().getLongUrl());
    }

    @Test
    public void testGetLongUrlWithParamError() {
        RequestDTO requestDTO = new RequestDTO();
        Result result = mockHttpRequest("/api/getLongUrl", requestDTO);

        Assert.assertEquals("获取长域名结果有误", ParamErrorCode.ILLEGAL_PARAM_ERROR.getErrorCode(), result.getErrorCode());
    }

    @Test
    public void testGetLongUrlWithNotExistError() {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setShortUrl("Zveqyq01");

        Result result = mockHttpRequest("/api/getLongUrl", requestDTO);

        Assert.assertEquals("获取长域名结果有误", ParamErrorCode.URL_NOT_EXIST_ERROR.getErrorCode(), result.getErrorCode());
    }

    @Test
    public void testGetShortUrl() {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setLongUrl("http://www.sequoiacap.cn/china/people/introduction/");
        requestDTO.setDigest("MD5");

        Result result = mockHttpRequest("/api/getShortUrl", requestDTO);

        Assert.assertEquals("获取短域名结果有误", "Zveqyq", null == result.getResponse() ? "" : result.getResponse().getShortUrl());

    }

    @Test
    public void testGetShortUrlWithParamError() {
        RequestDTO requestDTO = new RequestDTO();
        Result result = mockHttpRequest("/api/getShortUrl", requestDTO);

        Assert.assertEquals("获取短域名结果有误", ParamErrorCode.ILLEGAL_PARAM_ERROR.getErrorCode(), result.getErrorCode());
    }

    @Test
    public void testGetShortUrlWithSystemError() {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setLongUrl("http://www.sequoiacap.cn/china/people/introduction/212");
        requestDTO.setDigest("baiyang");

        Result result = mockHttpRequest("/api/getShortUrl", requestDTO);

        Assert.assertEquals("获取短域名结果有误", SystemErrorCode.SYSTEM_ERROR.getErrorCode(), result.getErrorCode());
    }

}

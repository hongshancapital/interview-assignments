package com.hszb.shorturl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.hszb.shorturl.BaseTestSupport;
import com.hszb.shorturl.model.ShortUrlResult;
import com.hszb.shorturl.model.request.QueryLongUrlRequest;
import com.hszb.shorturl.model.request.TransferShortUrlRequest;
import com.hszb.shorturl.model.response.BaseResponse;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: xxx
 * @License: (C) Copyright 2005-2019, xxx Corporation Limited.
 * @Date: 2021/12/20 2:18 下午
 * @Version: 1.0
 * @Description:
 */
public class ShortUrlControllerTest extends BaseTestSupport {

    private String shortCode;

    @Before
    public void transferShortUrl () throws Exception {
        TransferShortUrlRequest request = new TransferShortUrlRequest();
        List<String> longUrls = Arrays.asList("http://www.baidu.com", "http://www.souhu.com");
        request.setLongUrls(longUrls);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/shortSerivce/transferShortUrl")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(JSON.toJSONString(request));
        MockHttpServletResponse response = this.mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andReturn().getResponse();
        assert response.getStatus() == HttpStatus.OK.value();
        String responseStr = new String(response.getContentAsByteArray(), "UTF-8");
        BaseResponse<List<ShortUrlResult>> result = JSON.parseObject(responseStr, new TypeReference<BaseResponse<List<ShortUrlResult>>>(){});
        shortCode = result.getContent().get(0).getShortCode();
        assert result.checkSuccess();
    }

    @Test
    public void queryLongUrl () throws Exception {
        QueryLongUrlRequest request = new QueryLongUrlRequest();
        request.setShortCode(shortCode);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/shortSerivce/queryLongUrl")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(JSON.toJSONString(request));
        MockHttpServletResponse response = this.mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andReturn().getResponse();
        assert response.getStatus() == HttpStatus.OK.value();
        String responseStr = new String(response.getContentAsByteArray(), "UTF-8");
        BaseResponse<String> result = JSON.parseObject(responseStr, new TypeReference<BaseResponse<String>>() {
        });
        assert result.checkSuccess() && null != result.getContent();
    }
}

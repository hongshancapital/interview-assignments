package com.example.demo.domainnameservice.controller;

import com.example.demo.domainnameservice.constant.ErrorCode;
import com.example.demo.domainnameservice.entity.ApiResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * domain name controller test.
 *
 * @author laurent
 * @date 2021-12-11 下午4:43
 */
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@SuppressWarnings("unchecked")
public class DomainNameControllerTest {

    private static final String URL = "url";

    private static final String NORMAL_LONG_URL = "www.xxx.com";

    @SuppressWarnings("SpellCheckingInspection")
    private static final String LONG_LONG_URL = "www.xxxxxxxxxxxxxxxxxxxxxxxxxxx.com";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final String STORE_URL = "/domain/v1/store";

    private static final String VISIT_URL = "/domain/v1/visit";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void storeLongDomainName() throws Exception {
        RequestBuilder request = get(STORE_URL).param(URL, NORMAL_LONG_URL).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        ApiResult<String> apiResult = objectMapper.readValue(result.getResponse().getContentAsString(), ApiResult.class);
        Assert.assertEquals(8, apiResult.getData().length());
    }

    @Test
    public void storeLongLongDomainName() throws Exception {
        RequestBuilder request = get(STORE_URL).param(URL, LONG_LONG_URL).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        ApiResult<String> apiResult = objectMapper.readValue(result.getResponse().getContentAsString(), ApiResult.class);
        Assert.assertEquals(8, apiResult.getData().length());
    }

    @Test
    public void storeLEmptyDomainName() throws Exception {
        RequestBuilder request = get(STORE_URL).param(URL, "").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        ApiResult<String> apiResult = objectMapper.readValue(result.getResponse().getContentAsString(), ApiResult.class);
        Assert.assertEquals(ErrorCode.INVALID_INPUT, apiResult.getCode().intValue());
    }

    @Test
    public void readShortDomainName() throws Exception {
        // write first
        RequestBuilder request = get(STORE_URL).param(URL, NORMAL_LONG_URL).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        ApiResult<String> apiResult = objectMapper.readValue(result.getResponse().getContentAsString(), ApiResult.class);

        // then read
        request = get(VISIT_URL).param("url", apiResult.getData()).accept(MediaType.APPLICATION_JSON);
        result = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        apiResult = objectMapper.readValue(result.getResponse().getContentAsString(), ApiResult.class);
        Assert.assertEquals(NORMAL_LONG_URL, apiResult.getData());
    }

    @Test
    public void readShortDomainName_notExists() throws Exception {
        // then read
        RequestBuilder request = get(VISIT_URL).param("url", "AA").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        ApiResult<String>  apiResult = objectMapper.readValue(result.getResponse().getContentAsString(), ApiResult.class);
        Assert.assertEquals(ErrorCode.URL_NOT_FOUND, apiResult.getCode().intValue());
    }

}
package com.zmc.shorturl.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zmc.shorturl.service.impl.ShortUrlServiceImpl;
import com.zmc.shorturl.utils.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.StringUtils;

import static org.junit.Assert.*;


@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ShortUrlControllerTest {

    @Autowired
    private MockMvc mvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${default.domain}")
    public  String DEFAULT_DOMAIN;

    @Test
    public void testShortUrlNormal() throws Exception {
        String url = "/api/shortUrl?longUrl=";
        String longUrl = "http://www.baidu.com/chris/app?test=1234";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(url + longUrl)).
                andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertEquals(status, HttpStatus.OK.value());
        Result<String> result = objectMapper.readValue(response.getContentAsString(), new TypeReference<Result<String>>() {
        });
        assertNotNull(result);
        assertEquals(Result.SUCCESS, result.getErrorCode());
        assertFalse(StringUtils.isEmpty(result.getData()));
    }


    @Test
    public void testShortUrlNormalWithSame() throws Exception {
        String url = "/api/shortUrl?longUrl=";
        String longUrl = "http://www.baidu.com/chris/app?test=1234";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(url + longUrl)).
                andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertEquals(status, HttpStatus.OK.value());
        Result<String> result = objectMapper.readValue(response.getContentAsString(), new TypeReference<Result<String>>() {
        });
        assertNotNull(result);
        assertEquals(Result.SUCCESS, result.getErrorCode());
        assertFalse(StringUtils.isEmpty(result.getData()));
        mvcResult = mvc.perform(MockMvcRequestBuilders.post(url + longUrl)).
                andReturn();
        response = mvcResult.getResponse();
        Result<String> result1 = objectMapper.readValue(response.getContentAsString(), new TypeReference<Result<String>>() {
        });
        assertEquals(result.getData(), result1.getData());
    }

    @Test
    public void testShortUrWithNotSame() throws Exception {
        String url = "/api/shortUrl?longUrl=";
        String longUrl = "http://www.baidu.com/chris/app?test=1234";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(url + longUrl)).
                andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertEquals(status, HttpStatus.OK.value());
        Result<String> result = objectMapper.readValue(response.getContentAsString(), new TypeReference<Result<String>>() {
        });
        assertNotNull(result);
        assertEquals(Result.SUCCESS, result.getErrorCode());
        assertFalse(StringUtils.isEmpty(result.getData()));
        longUrl = "http://www.ddd.com/ddda/fdalfd/dakfjdfja";
        mvcResult = mvc.perform(MockMvcRequestBuilders.post(url + longUrl)).
                andReturn();
        response = mvcResult.getResponse();
        Result<String> result1 = objectMapper.readValue(response.getContentAsString(), new TypeReference<Result<String>>() {
        });
        assertNotEquals(result.getData(), result1.getData());
    }

    @Test
    public void testInvalidParam() throws Exception {
        String url = "/api/shortUrl?longUrl=";
        String longUrl = "www.baidu.com/chris/app?test=1234";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(url + longUrl)).
                andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertEquals(status, HttpStatus.BAD_REQUEST.value());
        Result<String> result = objectMapper.readValue(response.getContentAsString(), new TypeReference<Result<String>>() {
        });
        assertNotNull(result);
        assertEquals(Result.INVALID_PARAMETER, result.getErrorCode());
        assertTrue(StringUtils.isEmpty(result.getData()));
    }


    @Test
    public void testLongUrl() throws Exception {
        String url = "/api/longUrl?shortUrl=";
        String longUrl = "https://www.baidu.com/dddafd/dafdasf";
        String shortUrl = convert(longUrl);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(url + shortUrl)).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertEquals(status, HttpStatus.OK.value());
        Result<String> result = objectMapper.readValue(response.getContentAsString(), new TypeReference<Result<String>>() {
        });
        assertEquals(Result.SUCCESS, result.getErrorCode());
        assertEquals(result.getData(), longUrl);
    }

    @Test
    public void testLongUrl2() throws Exception {
        String url = "/api/longUrl?shortUrl=";
        String longUrl = "https://www.tc.com/test/1234";
        String shortUrl = convert(longUrl);
        shortUrl = shortUrl.replace(DEFAULT_DOMAIN, "");
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(url + shortUrl)).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertEquals(status, HttpStatus.OK.value());
        Result<String> result = objectMapper.readValue(response.getContentAsString(), new TypeReference<Result<String>>() {
        });
        assertEquals(Result.SUCCESS, result.getErrorCode());
        assertEquals(result.getData(), longUrl);
    }

    @Test
    public void testLongUrlWithEmpty() throws Exception {
        String url = "/api/longUrl?shortUrl=";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(url + "abcd1234")).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertEquals(status, HttpStatus.OK.value());
        Result<String> result = objectMapper.readValue(response.getContentAsString(), new TypeReference<Result<String>>() {
        });
        assertNull(result.getData());
    }


    private String convert(String longUrl) throws Exception {
        String url = "/api/shortUrl?longUrl=";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(url + longUrl)).
                andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        Result<String> result = objectMapper.readValue(response.getContentAsString(), new TypeReference<Result<String>>() {
        });
        return result.getData();
    }
}
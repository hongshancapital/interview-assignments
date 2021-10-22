package com.wh.franken.shorturl.app.controller;

import com.wh.franken.shorturl.app.vo.Result;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.StringUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ShortUrlControllerTest {

    @Autowired
    private MockMvc mvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testConvert2ShortUrlWithNormal() throws Exception {
        String url = "/api/shortUrl/convert2ShortUrl?longUrl=";
        String longUrl = "http://www.baidu.com/chris/app?id=10";
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
    public void testConvert2ShortUrlWithNormalWithSame() throws Exception {
        String url = "/api/shortUrl/convert2ShortUrl?longUrl=";
        String longUrl = "http://www.baidu.com/chris/app?id=10";
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
    public void testConvert2ShortUrlWithNomalWithNotSame() throws Exception {
        String url = "/api/shortUrl/convert2ShortUrl?longUrl=";
        String longUrl = "http://www.baidu.com/chris/app?id=10";
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
    public void testConvert2ShortUrlWithNomalWithInvalidParam() throws Exception {
        String url = "/api/shortUrl/convert2ShortUrl?longUrl=";
        String longUrl = "www.baidu.com/chris/app?id=10";
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
    public void testParseLongUrl() throws Exception {
        String url = "/api/shortUrl/parseLongUrl?shortUrl=";
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
    public void testParseLongUrl2() throws Exception {
        String url = "/api/shortUrl/parseLongUrl?shortUrl=";
        String longUrl = "https://www.baidu.com/dddafd/dafdasf";
        String shortUrl = convert(longUrl);
        shortUrl = shortUrl.replace(ShortUrlController.DEFAULT_DOMAIN, "");
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
    public void testParseLongUrlWithEmpty() throws Exception {
        String url = "/api/shortUrl/parseLongUrl?shortUrl=";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(url + "ddaddd")).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertEquals(status, HttpStatus.OK.value());
        Result<String> result = objectMapper.readValue(response.getContentAsString(), new TypeReference<Result<String>>() {
        });
        assertNull(result.getData());
    }


    private String convert(String longUrl) throws Exception {
        String url = "/api/shortUrl/convert2ShortUrl?longUrl=";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(url + longUrl)).
                andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        Result<String> result = objectMapper.readValue(response.getContentAsString(), new TypeReference<Result<String>>() {
        });
        return result.getData();
    }
}
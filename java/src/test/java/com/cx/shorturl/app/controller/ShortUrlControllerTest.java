package com.cx.shorturl.app.controller;

import com.cx.shorturl.app.vo.Result;
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

import static com.cx.shorturl.app.controller.ShortUrlController.DEFAULT_TINY_DOMAIN;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ShortUrlControllerTest {

    @Autowired
    private MockMvc mvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testConvert2ShortUrlWithNomal() throws Exception {
        String url = "/api/shortUrl/convert2ShortUrl?longUrl=";
        String longUrl = "http://www.baidu.com/chris/app?id=10";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(url + longUrl)).
                andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertTrue(status == HttpStatus.OK.value());
        Result<String> result = objectMapper.readValue(response.getContentAsString(), new TypeReference<Result<String>>() {
        });
        assertTrue(result != null);
        assertTrue(result.getErrorCode() == Result.SUCCESS);
        assertTrue(!StringUtils.isEmpty(result.getData()));
    }


    @Test
    public void testConvert2ShortUrlWithNomalWithSame() throws Exception {
        String url = "/api/shortUrl/convert2ShortUrl?longUrl=";
        String longUrl = "http://www.baidu.com/chris/app?id=10";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(url + longUrl)).
                andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertTrue(status == HttpStatus.OK.value());
        Result<String> result = objectMapper.readValue(response.getContentAsString(), new TypeReference<Result<String>>() {
        });
        assertTrue(result != null);
        assertTrue(result.getErrorCode() == Result.SUCCESS);
        assertTrue(!StringUtils.isEmpty(result.getData()));
        mvcResult = mvc.perform(MockMvcRequestBuilders.post(url + longUrl)).
                andReturn();
        response = mvcResult.getResponse();
        Result<String> result1 = objectMapper.readValue(response.getContentAsString(), new TypeReference<Result<String>>() {
        });
        assertTrue(result.getData().equals(result1.getData()));
    }

    @Test
    public void testConvert2ShortUrlWithNomalWithNotSame() throws Exception {
        String url = "/api/shortUrl/convert2ShortUrl?longUrl=";
        String longUrl = "http://www.baidu.com/chris/app?id=10";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(url + longUrl)).
                andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertTrue(status == HttpStatus.OK.value());
        Result<String> result = objectMapper.readValue(response.getContentAsString(), new TypeReference<Result<String>>() {
        });
        assertTrue(result != null);
        assertTrue(result.getErrorCode() == Result.SUCCESS);
        assertTrue(!StringUtils.isEmpty(result.getData()));
        longUrl = "http://www.ddd.com/ddda/fdalfd/dakfjdfja";
        mvcResult = mvc.perform(MockMvcRequestBuilders.post(url + longUrl)).
                andReturn();
        response = mvcResult.getResponse();
        Result<String> result1 = objectMapper.readValue(response.getContentAsString(), new TypeReference<Result<String>>() {
        });
        assertTrue(!result.getData().equals(result1.getData()));
    }

    @Test
    public void testConvert2ShortUrlWithNomalWithInvalidParam() throws Exception {
        String url = "/api/shortUrl/convert2ShortUrl?longUrl=";
        String longUrl = "www.baidu.com/chris/app?id=10";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(url + longUrl)).
                andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertTrue(status == HttpStatus.BAD_REQUEST.value());
        Result<String> result = objectMapper.readValue(response.getContentAsString(), new TypeReference<Result<String>>() {
        });
        assertTrue(result != null);
        assertTrue(result.getErrorCode() == Result.INVALID_PARAMETER);
        assertTrue(StringUtils.isEmpty(result.getData()));
    }


    @Test
    public void testGetLongUrl() throws Exception {
        String url = "/api/shortUrl/getLongUrl?shortUrl=";
        String longUrl = "https://www.baidu.com/dddafd/dafdasf";
        String shortUrl = convert(longUrl);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(url + shortUrl)).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertTrue(status == HttpStatus.OK.value());
        Result<String> result = objectMapper.readValue(response.getContentAsString(), new TypeReference<Result<String>>() {
        });
        assertTrue(result.getErrorCode() == Result.SUCCESS);
        assertTrue(result.getData().equals(longUrl));
    }

    @Test
    public void testGetLongUrl2() throws Exception {
        String url = "/api/shortUrl/getLongUrl?shortUrl=";
        String longUrl = "https://www.baidu.com/dddafd/dafdasf";
        String shortUrl = convert(longUrl);
        shortUrl = shortUrl.replace(DEFAULT_TINY_DOMAIN, "");
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(url + shortUrl)).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertTrue(status == HttpStatus.OK.value());
        Result<String> result = objectMapper.readValue(response.getContentAsString(), new TypeReference<Result<String>>() {
        });
        assertTrue(result.getErrorCode() == Result.SUCCESS);
        assertTrue(result.getData().equals(longUrl));
    }

    @Test
    public void testGetLongUrlWithEmpty() throws Exception {
        String url = "/api/shortUrl/getLongUrl?shortUrl=";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(url + "ddaddd")).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertTrue(status == HttpStatus.OK.value());
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
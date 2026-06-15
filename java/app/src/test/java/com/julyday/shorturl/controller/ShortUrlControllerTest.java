package com.julyday.shorturl.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.julyday.shorturl.vo.Result;
import org.apache.commons.lang3.StringUtils;
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

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ShortUrlControllerTest {

    private static final String PRE_URI = "/api/url/";

    @Autowired
    private MockMvc mvc;

    private ObjectMapper objectMapper = new ObjectMapper();


    /*
     * 转短域名测试.
     */
    @Test
    public void testConvert2ShortUrl() throws Exception {
        String url = PRE_URI + "short?longUrl=";
        String longUrl = "http://www.julyday.com/test/01";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(url + longUrl)).
                andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertTrue(status == HttpStatus.OK.value());
        Result<String> result = objectMapper.readValue(response.getContentAsString(), new TypeReference<Result<String>>() {
        });
        assertTrue(result != null);
        assertTrue(result.getCode() == Result.SUCCESS);
        assertTrue(!StringUtils.isEmpty(result.getData()));
    }

    /*
     * 转短域名测试-无效参数.
     */
    @Test
    public void testConvert2ShortUrlWithInvalidParam() throws Exception {
        String url = PRE_URI + "short?longUrl=";
        String longUrl = "www.julyday.com/test/01";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(url + longUrl)).
                andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertTrue(status == HttpStatus.BAD_REQUEST.value());
        Result<String> result = objectMapper.readValue(response.getContentAsString(), new TypeReference<Result<String>>() {
        });
        assertTrue(result != null);
        assertTrue(result.getCode() == Result.INVALID_PARAMETER);
        assertTrue(StringUtils.isEmpty(result.getData()));
    }

    /*
     * 相同长域名多次转短域名测试，短域名相同.
     */
    @Test
    public void testConvert2ShortUrlWithSame() throws Exception {
        String url = PRE_URI + "short?longUrl=";
        String longUrl = "http://www.julyday.com/test/01";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(url + longUrl)).
                andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertTrue(status == HttpStatus.OK.value());
        Result<String> result1 = objectMapper.readValue(response.getContentAsString(), new TypeReference<Result<String>>() {
        });
        assertTrue(result1 != null);
        assertTrue(result1.getCode() == Result.SUCCESS);
        assertTrue(!StringUtils.isEmpty(result1.getData()));
        mvcResult = mvc.perform(MockMvcRequestBuilders.post(url + longUrl)).
                andReturn();
        response = mvcResult.getResponse();
        Result<String> result2 = objectMapper.readValue(response.getContentAsString(), new TypeReference<Result<String>>() {
        });
        assertTrue(result2 != null);
        assertTrue(result1.getData().equals(result2.getData()));
    }

    /*
     * 不同长域名转短域名不相同.
     */
    @Test
    public void testConvert2ShortUrlWithNotSame() throws Exception {
        String url = PRE_URI + "short?longUrl=";
        String longUrl = "http://www.julyday.com/test/01";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(url + longUrl)).
                andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertTrue(status == HttpStatus.OK.value());
        Result<String> result1 = objectMapper.readValue(response.getContentAsString(), new TypeReference<Result<String>>() {
        });
        assertTrue(result1 != null);
        assertTrue(result1.getCode() == Result.SUCCESS);
        longUrl = "http://www.julyday.com/test/02";
        assertTrue(!StringUtils.isEmpty(result1.getData()));
        mvcResult = mvc.perform(MockMvcRequestBuilders.post(url + longUrl)).
                andReturn();
        response = mvcResult.getResponse();
        Result<String> result2 = objectMapper.readValue(response.getContentAsString(), new TypeReference<Result<String>>() {
        });
        assertTrue(result2 != null);
        assertTrue(!result1.getData().equals(result2.getData()));
    }

    /*
     * 短域名和长域名匹配.
     */
    @Test
    public void testGetLongUrl() throws Exception {
        String url = PRE_URI + "short?longUrl=";
        String longUrl = "http://www.julyday.com/test/01";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(url + longUrl)).
                andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertTrue(status == HttpStatus.OK.value());
        Result<String> result1 = objectMapper.readValue(response.getContentAsString(), new TypeReference<Result<String>>() {
        });
        assertTrue(result1 != null);
        String shortUrl = result1.getData();

        url = PRE_URI + "long?shortUrl=";
        mvcResult = mvc.perform(MockMvcRequestBuilders.get(url + shortUrl)).andReturn();
        response = mvcResult.getResponse();
        status = response.getStatus();
        assertTrue(status == HttpStatus.OK.value());
        Result<String> result2 = objectMapper.readValue(response.getContentAsString(), new TypeReference<Result<String>>() {
        });
        assertTrue(result2 != null);
        assertTrue(result2.getCode() == Result.SUCCESS);
        assertTrue(result2.getData().equals(longUrl));
    }

    /*
     * 短域名无域名和长域名匹配.
     */
    @Test
    public void testGetLongUrlWithoutPrefix() throws Exception {
        String url = PRE_URI + "short?longUrl=";
        String longUrl = "http://www.julyday.com/test/01";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(url + longUrl)).
                andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertTrue(status == HttpStatus.OK.value());
        Result<String> result1 = objectMapper.readValue(response.getContentAsString(), new TypeReference<Result<String>>() {
        });
        assertTrue(result1 != null);
        String shortUrl = result1.getData();
        shortUrl = shortUrl.replace(ShortUrlController.DEFAULT_PREFIX, "");
        url = PRE_URI + "long?shortUrl=";
        mvcResult = mvc.perform(MockMvcRequestBuilders.get(url + shortUrl)).andReturn();
        response = mvcResult.getResponse();
        status = response.getStatus();
        assertTrue(status == HttpStatus.OK.value());
        Result<String> result2 = objectMapper.readValue(response.getContentAsString(), new TypeReference<Result<String>>() {
        });
        assertTrue(result2 != null);
        assertTrue(result2.getCode() == Result.SUCCESS);
        assertTrue(result2.getData().equals(longUrl));
    }

    /*
     * 短域名无域名和长域名匹配.
     */
    @Test
    public void testGetLongUrlNotExist() throws Exception {
        String shortUrl = "http://c.j.cn/123456";
        String url = PRE_URI + "long?shortUrl=" + shortUrl;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(url + shortUrl)).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertTrue(status == HttpStatus.OK.value());
        Result<String> result = objectMapper.readValue(response.getContentAsString(), new TypeReference<Result<String>>() {
        });
        assertTrue(result != null);
        assertNull(result.getData());
    }

}

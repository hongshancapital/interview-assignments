package com.redwood.shorturl.test;

import com.alibaba.fastjson.JSONObject;
import com.redwood.shorturl.common.BizException;
import com.redwood.shorturl.domain.Result;
import com.redwood.shorturl.service.inter.ShortUrlService;
import com.redwood.shorturl.test.inter.BaseJunit;
import com.redwood.shorturl.util.NumericConvertUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author zhengyin
 */
public class ShortUrlControllerTest extends BaseJunit {

    @Autowired
    private ShortUrlService shortUrlService;

    @Test
    public void testGenAndGet() {
        String mainUrl1 = "http://www.google.com";
        String shortUrl = shortUrlService.generate(mainUrl1);
        Assert.assertNotNull(shortUrl);
        String mainUrl2 = shortUrlService.exchange(shortUrl);
        Assert.assertEquals(mainUrl1, mainUrl2);
    }

    @Test
    public void testRepeatSaveAndGet() {
        String mainUrl = "http://www.google.com";
        String shortUrl1 = shortUrlService.generate(mainUrl);
        String shortUrl2 = shortUrlService.generate(mainUrl);
        Assert.assertEquals(shortUrl1, shortUrl2);
    }

    @Test
    public void testHaveNoShortUrl() throws Exception {
        String shortUrl = "http://f11.cn/123456";
        Result result = generate(shortUrl, "/exchange/mainUrl", "shortUrl");
        Assert.assertEquals(result.getStatus(), 400);
        Assert.assertEquals(result.getMessage(), "Have no mainUrl.");
    }

    @Test
    public void testSaveAndGet() throws Exception {
        Result result = generate("https://www.google.com/mail", "/shortUrl/generate", "mainUrl");
        Assert.assertEquals(result.getStatus(), 200);
    }

    @Test
    public void testGenerateAndExchange() throws Exception {
        String sourceUrl = "http://www.google.com/mail";
        Result result = generate(sourceUrl, "/shortUrl/generate", "mainUrl");
        Assert.assertEquals(result.getStatus(), 200);
        String shortUrl = String.valueOf(result.getData());
        Result mainUrlResponseResult = generate(shortUrl, "/exchange/mainUrl", "shortUrl");
        Assert.assertEquals(mainUrlResponseResult.getStatus(), 200);
        Assert.assertEquals(mainUrlResponseResult.getData(), sourceUrl);
    }

    @Test
    public void testGenerateRepeat() throws Exception {
        String sourceUrl = "http://www.google.com/mail";
        Result result1 = generate(sourceUrl, "/shortUrl/generate", "mainUrl");
        Result result2 = generate(sourceUrl, "/shortUrl/generate", "mainUrl");
        Assert.assertEquals(result1.getStatus(), 200);
        Assert.assertEquals(result2.getStatus(), 200);
        Assert.assertEquals(result1.getData(), result2.getData());
    }

    @Test
    public void testExceptionParam() throws Exception {
        String sourceUrl = "www.google.com/mail";
        Result result1 = generate(sourceUrl, "/shortUrl/generate", "mainUrl");
        Assert.assertEquals(result1.getStatus(), 402);
        String sourceUrl2 = "";
        Result result2 = generate(sourceUrl2, "/shortUrl/generate", "mainUrl");
        Assert.assertEquals(result2.getStatus(), 403);
    }

    @Test
    public void testNumericConvertUtils() throws Exception {
        String s1 = NumericConvertUtils.toOtherNumberSystem(0, 62);
        String s3 = NumericConvertUtils.toOtherNumberSystem(300, 62);
        Assert.assertNotSame(s1, s3);
    }

    @Test
    public void testNumericConvertException() {
        try {
            String s4 = NumericConvertUtils.toOtherNumberSystem(-100, 62);
        } catch (BizException e) {
            Assert.assertNotNull(e);
        }
    }

    private Result generate(String sourceUrl, String s, String mainUrl2) throws Exception {
        MockHttpServletRequestBuilder mainUrl = get(s)
                .contentType(MediaType.APPLICATION_JSON)
                .param(mainUrl2, sourceUrl);
        String responseString = mockMvc.perform(mainUrl).andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        return JSONObject.parseObject(responseString, Result.class);
    }
} 
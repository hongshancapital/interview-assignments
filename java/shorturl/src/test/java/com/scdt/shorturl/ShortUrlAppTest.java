package com.scdt.shorturl;

import com.scdt.shorturl.service.ShortUrlService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 短URL服务 应用测试
 * @author niuyi
 * @since  2021-12-10
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShortUrlAppTest {
    //短URL服务
    @Autowired
    private ShortUrlService shortUrlService;

    private MockMvc mockMvc;
    @Autowired
    protected WebApplicationContext wac;

    @Before  //这个方法在每个方法执行之前都会执行一遍
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();  //初始化MockMvc对象
    }

    @Rule
    public ExpectedException thrown= ExpectedException.none();

    /**
     * 基于controller的测试
     * @throws Exception
     */
    @Test
    public void validateShortUrl() throws Exception {
        String longUrl = "bbc.com";
        //生成shortUrl
        RequestBuilder requestShortUrl = MockMvcRequestBuilders.post("/api/toShortURL")
                .param("longUrl", longUrl);
        String shortUrl = mockMvc.perform(requestShortUrl)
                .andExpect(status().isOk())    //返回的状态是200
                .andDo(print())         //打印出请求和相应的内容
                .andReturn().getResponse().getContentAsString();   //将相应的数据转换为字符串
        System.out.println("toShortURL返回:" + shortUrl);
        //shortUrl还原成longUrl
        RequestBuilder requestLongUrl = MockMvcRequestBuilders.post("/api/toLongUrl")
                .param("shortURL", shortUrl);
        String longUrlReturn = mockMvc.perform(requestLongUrl)
                .andExpect(status().isOk())    //返回的状态是200
                .andDo(print())         //打印出请求和相应的内容
                .andReturn().getResponse().getContentAsString();   //将相应的数据转换为字符串
        System.out.println("toLongUrl返回:" + longUrlReturn);

        Assert.assertEquals(longUrl, longUrlReturn);
    }

    /**
     * 测试短URL服务  长变短功能
     */
    @Test
    public void testToShort() {
        String shortUrl = shortUrlService.toShortURL("baidu.com");
        System.out.println(shortUrl);
    }

    /**
     * 测试短URL服务  长变短功能 空参数 有异常的情况下
     */
    @Test//(expected = IllegalArgumentException.class)
    public void testToShortNullValue() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("longUrl为空");
        String shortUrl = shortUrlService.toShortURL(null);
    }

    /**
     * 测试短URL服务  长变短功能 URL过长 有异常的情况下
     */
    @Test//(expected = IllegalArgumentException.class)
    public void testToShortUrlTooLong() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("URL过长");
        String longurl="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_"+"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_"
                +"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_"+"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_"
                +"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_";
        String shortUrl = shortUrlService.toShortURL(longurl);
    }

    /**
     * 测试短URL服务  长变短 短还原 功能
     */
    @Test
    public void testToLong() {
        String longUrl = "bbc.com";
        String shortUrl = shortUrlService.toShortURL(longUrl);
        String longUrlReturn = shortUrlService.toLongUrl(shortUrl);
        Assert.assertEquals(longUrl, longUrlReturn);
    }


}

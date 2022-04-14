package com.mjl;

import com.alibaba.fastjson.TypeReference;
import com.mjl.api.ShortUrlService;
import com.mjl.exception.BusinessException;
import com.mjl.model.GetLongUrlRequest;
import com.mjl.model.GetShortUrlRequest;
import com.mjl.model.Response;
import com.mjl.repository.ShortUrlRepositoryImpl;
import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.alibaba.fastjson.JSON;


import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ShortUrlTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ShortUrlRepositoryImpl shortUrlRepository;

    @Autowired
    private ShortUrlService shortUrlService;

    @Test
    @DisplayName("测试不合法的url")
    public void testInvalidUrl() throws Exception {
        String originalUrl = "www.baidu1.com";

        String shortUrlResp = getShortUrlFromLongUrl(originalUrl);
        Assertions.assertTrue(shortUrlResp.contains("GET-SHORT-URL-INVALID-FORMAT-ERROR"));
    }

    @Test
    @DisplayName("测试url生成和验证")
    public void testValidateShortUrl() throws Exception {
        String originalUrl = "https://www.baidu2.com";

        String shortUrlResp = getShortUrlFromLongUrl(originalUrl);
        String shortUrl = getModule(shortUrlResp);

        Assertions.assertTrue(shortUrl.startsWith("http://localhost:8080/s/"));
        String shortUrlSuffix = shortUrl.substring(24);
        Assertions.assertEquals(shortUrlSuffix.length(), 8);

        String longUrlResp = getLongUrlFromShortUrlSuffix(shortUrlSuffix);
        Assertions.assertEquals(originalUrl, getModule(longUrlResp));
        Assertions.assertEquals(shortUrl, getModule(getShortUrlFromLongUrl(getModule(longUrlResp))));

        longUrlResp = getLongUrlFromShortUrl(shortUrl);
        Assertions.assertEquals(originalUrl, getModule(longUrlResp));

    }




    @Test
    @DisplayName("测试不存在的短链接")
    public void testNotExistShortUrl() throws Exception {
        String longUrlResp = getLongUrlFromShortUrlSuffix("12312312");
        Assertions.assertNull(getModule(longUrlResp));

    }

    @Test
    @DisplayName("service层测试不存在的短链接")
    public void testGenerateShortUrl() throws Exception {
        BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> {
            shortUrlService.generateShortUrl("abc");
        });
        Assertions.assertTrue(exception.getMessage().contains("GET-SHORT-URL-INVALID-FORMAT-ERROR"));
    }

    @Test
    @DisplayName("service层测试长链接格式非法")
    public void testGetShortUrlSuffix() throws Exception {
        BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> {
            shortUrlService.getShortUrlSuffix(null);
        });
        Assertions.assertTrue(exception.getMessage().contains("GET-SHORT-URL-INVALID-FORMAT-ERROR"));
    }

    @Test
    @DisplayName("service层测试短链接格式非法")
    public void testGetLongUrl() throws Exception {
        BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> {
            shortUrlService.getLongUrl("");
        });
        Assertions.assertTrue(exception.getMessage().contains("GET-LONG-URL-INVALID-FORMAT-ERROR"));

        exception = Assertions.assertThrows(BusinessException.class, () -> {
            shortUrlService.getLongUrl("abcabc");
        });
        Assertions.assertTrue(exception.getMessage().contains("GET-LONG-URL-INVALID-PREFIX-ERROR"));


    }

    @Test
    @DisplayName("测试清理内存场景")
    public void testClearCache() throws Exception {
        ArrayList<String> urlList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            urlList.add("https://www.baidu"+ i + ".com");
        }

        ShortUrlRepositoryImpl.setExpireTime(1000);
        ShortUrlRepositoryImpl.setMaxSize(49);
        String shortUrl;
        String shortUrlResp;
        for (int i = 0; i < 49; i++) {
            shortUrlResp = getShortUrlFromLongUrl(urlList.get(i));
            shortUrl = getModule(shortUrlResp);
            Assertions.assertTrue(shortUrl.startsWith("http://localhost:8080/s/"));
        }
        shortUrlResp = getShortUrlFromLongUrl(urlList.get(50));
        shortUrl = getModule(shortUrlResp);
        Assertions.assertNull(shortUrl);

        Thread.sleep(1500);
        for (int i = 50; i < 99; i++) {
            shortUrlResp = getShortUrlFromLongUrl(urlList.get(i));
            shortUrl = getModule(shortUrlResp);
            Assertions.assertTrue(shortUrl.startsWith("http://localhost:8080/s/"));
        }
        shortUrlRepository.clear();
        ShortUrlRepositoryImpl.setExpireTime(10 * 60 * 1000);
        ShortUrlRepositoryImpl.setMaxSize(1000000L);


    }

    @Test
    @DisplayName("测试短链接超时场景")
    public void testShortUrlTimeout() throws Exception {
        String originalUrl = "https://www.baidu3.com";
        ShortUrlRepositoryImpl.setExpireTime(1000);
        String shortUrlResp = getShortUrlFromLongUrl(originalUrl);
        String shortUrl = getModule(shortUrlResp);

        Assertions.assertTrue(shortUrl.startsWith("http://localhost:8080/s/"));
        String shortUrlSuffix = shortUrl.substring(24);
        Assertions.assertEquals(shortUrlSuffix.length(), 8);


        Thread.sleep(1500);

        String longUrlResp = getLongUrlFromShortUrlSuffix(shortUrlSuffix);
        String longUrl = getModule(longUrlResp);
        ShortUrlRepositoryImpl.setExpireTime(10 * 60 * 1000);
    }

    @Test
    @DisplayName("测试长链接超时场景")
    public void testLongUrlTimeout() throws Exception {
        String originalUrl = "https://www.baidu4.com";

        ShortUrlRepositoryImpl.setExpireTime(1000);
        String shortUrlResp = getShortUrlFromLongUrl(originalUrl);
        String shortUrl = getModule(shortUrlResp);

        Assertions.assertTrue(shortUrl.startsWith("http://localhost:8080/s/"));
        String shortUrlSuffix = shortUrl.substring(24);
        Assertions.assertEquals(shortUrlSuffix.length(), 8);

        Thread.sleep(1500);

        // 超时重新生成了
        String shortUrlResp1 = getShortUrlFromLongUrl(originalUrl);
        shortUrl = getModule(shortUrlResp);
        Assertions.assertTrue(shortUrl.startsWith("http://localhost:8080/s/"));

        ShortUrlRepositoryImpl.setExpireTime(10 * 60 * 1000);

    }


    private String getShortUrlFromLongUrl(String longUrl) throws Exception {
        GetShortUrlRequest getShortUrlRequest = new GetShortUrlRequest();
        getShortUrlRequest.setLongUrl(longUrl);
        MvcResult mvcResult = mockMvc.perform(
                        post("/getShortUrl")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(  JSON.toJSONString(getShortUrlRequest)))
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println(result);
        return result;
    }

    private String getLongUrlFromShortUrlSuffix(String shortUrlSuffix) throws Exception {
        GetLongUrlRequest getLongUrlRequest = new GetLongUrlRequest();
        getLongUrlRequest.setShortUrlSuffix(shortUrlSuffix);
        MvcResult mvcResult = mockMvc.perform(
                        post("/getLongUrlBySuffix")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(  JSON.toJSONString(getLongUrlRequest)))
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println(result);
        return result;
    }

    private String getLongUrlFromShortUrl(String shortUrl) throws Exception {
        GetLongUrlRequest getLongUrlRequest = new GetLongUrlRequest();
        getLongUrlRequest.setShortUrl(shortUrl);
        MvcResult mvcResult = mockMvc.perform(
                        post("/getLongUrl")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(  JSON.toJSONString(getLongUrlRequest)))
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println(result);
        return result;
    }

    public String getModule(String respStr) {
        Response respObj = JSON.parseObject(respStr, Response.class);
        return (String) respObj.getModule();
    }

}

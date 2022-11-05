package com.interview.wph.shorturl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wangpenghao
 * @date 2022/4/24 15:09
 * controller 测试类
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ShortUrlControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String longUrl = "http://baidu.com";
    private static String shortUrl = null;

    @Test
    @DisplayName("测试获取短链接(post)")
    @Order(1)
    public void getShortUrl() {
        testPost();
    }


    @Test
    @DisplayName("测试获取长链接(get)")
    @Order(3)
    public void getLongUrl() {
        testGet();
    }


    private void testPost() {
        URI uri = UriComponentsBuilder.fromUriString("/short-url/?longUrl=" + longUrl).build().encode().toUri();
        String resultStr = this.restTemplate.postForObject(uri, null, String.class);
        assertNotNull(resultStr);
        JSONObject jsonObject = JSONUtil.parseObj(resultStr);
        Integer code = jsonObject.get("code", Integer.class);
        assertEquals(code, 201);
        String data = jsonObject.get("data", String.class);
        assertTrue(data != null && data.length() <= 8);
        shortUrl = data;
    }


    private void testGet() {
        URI uri = UriComponentsBuilder.fromUriString("/short-url/?shortUrl=" + shortUrl).build().encode().toUri();
        String resultStr = this.restTemplate.getForObject(uri, String.class);
        assertNotNull(resultStr);
        JSONObject jsonObject = JSONUtil.parseObj(resultStr);
        Integer code = jsonObject.get("code", Integer.class);
        assertEquals(code, 200);
        String data = jsonObject.get("data", String.class);
        assertEquals(data, longUrl);
    }

    @Test
    @DisplayName("测试缓存不命中或者异常")
    @Order(3)
    public void getExceptionOrNoCache() {
        testGetError();
        testGetError2();
        testGetError3();
        testPostError();
        testNocache();
        testNotFound();
    }

    /**
     * 缓存不命中
     */
    private void testGetError() {
        URI uri = UriComponentsBuilder.fromUriString("/short-url/?shortUrl=error").build().encode().toUri();
        String resultStr = this.restTemplate.getForObject(uri, String.class);
        assertNotNull(resultStr);
        JSONObject jsonObject = JSONUtil.parseObj(resultStr);
        Integer code = jsonObject.get("code", Integer.class);
        assertEquals(code, 404);
    }

    /**
     * 非法参数
     */
    private void testGetError2() {
        URI uri = UriComponentsBuilder.fromUriString("/short-url/?shortUrl=").build().encode().toUri();
        String resultStr = this.restTemplate.getForObject(uri, String.class);
        assertNotNull(resultStr);
        JSONObject jsonObject = JSONUtil.parseObj(resultStr);
        Integer code = jsonObject.get("code", Integer.class);
        assertEquals(code, 400);
    }

    /**
     * 非法参数
     */
    private void testGetError3() {
        URI uri = UriComponentsBuilder.fromUriString("/short-url/?shortUrl=sjkshhshdhs").build().encode().toUri();
        String resultStr = this.restTemplate.getForObject(uri, String.class);
        assertNotNull(resultStr);
        JSONObject jsonObject = JSONUtil.parseObj(resultStr);
        Integer code = jsonObject.get("code", Integer.class);
        assertEquals(code, 400);
    }

    private void testPostError() {
        URI uri = UriComponentsBuilder.fromUriString("/short-url/?longUrl=").build().encode().toUri();
        String resultStr = this.restTemplate.postForObject(uri, null, String.class);
        assertNotNull(resultStr);
        JSONObject jsonObject = JSONUtil.parseObj(resultStr);
        Integer code = jsonObject.get("code", Integer.class);
        assertEquals(code, 400);
    }

    private void testNocache() {
//        qIkm6Fm0
        URI uri = UriComponentsBuilder.fromUriString("/short-url/?shortUrl=qIkm6Fm0").build().encode().toUri();
        String resultStr = this.restTemplate.getForObject(uri, String.class);
        assertNotNull(resultStr);
        JSONObject jsonObject = JSONUtil.parseObj(resultStr);
        Integer code = jsonObject.get("code", Integer.class);
        assertEquals(code, 404);
    }


    private void testNotFound() {
//        qIkm6Fm0
        URI uri = UriComponentsBuilder.fromUriString("/short-url/test/?shortUrl=qIkm6Fm0").build().encode().toUri();
        String resultStr = this.restTemplate.getForObject(uri, String.class);
        assertNotNull(resultStr);
        JSONObject jsonObject = JSONUtil.parseObj(resultStr);
        Integer code = jsonObject.get("status", Integer.class);
        assertEquals(code, 404);
    }
}

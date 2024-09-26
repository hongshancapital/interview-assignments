package com.wenchao.jacoco.demo;

import com.wenchao.jacoco.demo.utils.Ret;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Wenchao Gong
 * @date 2021/12/15 15:53
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UrlTest {

    public static final String TO_SHORT_URL = "http://localhost:%d/url/to-short?longUrl=%s";
    public static final String TO_LONG_URL = "http://localhost:%d/url/to-long?shortUrl=%s";
    private static final Map<String, String> results = new HashMap<>();

    //使用@LocalServerPort将端口注入进来
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testToShort() {
        //生成随机字符串
        for (int i = 0; i < 12000; i++) {
            String longUrl = RandomStringUtils.randomAlphanumeric(1, 255);
            Ret<String> result = doGet(TO_SHORT_URL, longUrl);
            Assert.assertEquals("长域名转短域名失败", 200, result.getCode());
            results.put(longUrl, result.getData());
        }
    }

    @Test
    public void testToLong() {
        Iterator<Map.Entry<String, String>> iterator = results.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            String shortUrl = next.getValue();
            Ret<String> result = doGet(TO_LONG_URL, shortUrl);
            Assert.assertEquals("短域名转长域名失败", 200, result.getCode());
            Assert.assertEquals("短域名转长域名错误", next.getKey(), result.getData());
        }
    }

    /**
     * 请求接口
     *
     * @param template
     * @param param
     * @return
     */
    private Ret<String> doGet(String template, String param) {
        Ret<String> result = this.restTemplate.getForObject(String.format(template, port, param), Ret.class);
        Assert.assertNotNull("返回结果为空", result);
        return result;
    }
}

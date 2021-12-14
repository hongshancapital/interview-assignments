package com.example.tinyurl.service;

import com.example.tinyurl.util.SpringBeanUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TinyurlServiceTest {
    private static final String TEST_URL = "http://www.baidu.com";

    /**
     * 对网址先编码获取短网址，再通过该短网址查询到的网址保持不变
     */
    @Test
    public void encode_decode_accept_normal() {
        TinyurlService tinyurlService = SpringBeanUtil.getBean(TinyurlService.class);
        String tinyUrl = tinyurlService.encode(TEST_URL);
        Assert.assertEquals(TEST_URL, tinyurlService.decode(tinyUrl));
    }

    /**
     * 同一网址转码多次，短URL一致
     */
    @Test
    public void encode_accept_bigdata() {
        TinyurlService tinyurlService = SpringBeanUtil.getBean(TinyurlService.class);
        String tinyUrl = tinyurlService.encode(TEST_URL);
        for (int i = 0; i < 1000000; i++) {
            Assert.assertEquals(tinyUrl, tinyurlService.encode(TEST_URL));
        }
    }
}
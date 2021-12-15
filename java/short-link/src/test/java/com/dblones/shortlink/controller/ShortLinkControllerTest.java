package com.dblones.shortlink.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShortLinkControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void store() {
        String url = "https://www.dblones.com/index.html";
        ResponseEntity responseEntity = restTemplate.getForEntity("/store?url=" + url, String.class);
        int statusCode = responseEntity.getStatusCodeValue();
        String result = (String) responseEntity.getBody();
        System.out.println("status:" + statusCode + ", result:" + result);
    }

    @Test
    public void storeEmpty() {
        String url = "";
        ResponseEntity responseEntity = restTemplate.getForEntity("/store?url=" + url, String.class);
        int statusCode = responseEntity.getStatusCodeValue();
        String result = (String) responseEntity.getBody();
        System.out.println("status:" + statusCode + ", result:" + result);
    }

    @Test
    public void get() {
        String url = "s.cn/s";
        ResponseEntity responseEntity = restTemplate.getForEntity("/get?url=" + url, String.class);
        int statusCode = responseEntity.getStatusCodeValue();
        String result = (String) responseEntity.getBody();
        System.out.println("status:" + statusCode + ", result:" + result);
    }


    @Test
    public void getEmpty() {
        String url = "";
        ResponseEntity responseEntity = restTemplate.getForEntity("/get?url=" + url, String.class);
        int statusCode = responseEntity.getStatusCodeValue();
        String result = (String) responseEntity.getBody();
        System.out.println("status:" + statusCode + ", result:" + result);
    }
}

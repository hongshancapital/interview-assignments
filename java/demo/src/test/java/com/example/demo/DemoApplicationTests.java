package com.example.demo;

import com.example.demo.service.StorageService;
import com.example.demo.service.UrlService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author: dsm
 * @Description: 单元测试类
 * @Date Create in 2021/12/23
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.DEFINED_PORT)
class DemoApplicationTests {
    @Resource
    private UrlService urlService;
    @Resource
    private StorageService storageService;
    @Resource
    private TestRestTemplate restTemplate;
    @Resource
    private ObjectMapper objectMapper;
    @Test
    void shortUrl() {
        String[] resUrl=urlService.shortUrl("https://zhidao.baidu.com/");
        for (String url : resUrl) {
            System.out.println(url);
        }
    }

    @Test
    void storageService() {
        String resUrl=storageService.AreaNameStorage("https://zhidao.baidu.com/");
        System.out.println(resUrl);
        String url=storageService.AreaNameRead(resUrl);
        System.out.println(url);
    }

    @Test
    void areaNameStorag() {
        HttpHeaders httpHeaders=new HttpHeaders();
        String result1=this.restTemplate.postForObject("/areaNameController/areaNameStorag?areaName=https://zhidao.baidu.com/", new HttpEntity <String>(httpHeaders), String.class);
        System.out.println(result1);
    }

    @Test
    void AreaNameController() throws JsonProcessingException {
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, "token");
        String result1=this.restTemplate.postForObject("/areaNameController/areaNameStorag?areaName=https://zhidao.baidu.com/", new HttpEntity <String>(httpHeaders), String.class);
        JsonNode jsonNode=objectMapper.readTree(result1);
        int code=jsonNode.get("code").intValue();
        if (code == 200) {
            String url="/areaNameController/areaNameRead?areaName=" + jsonNode.get("data").asText();
            String result2=this.restTemplate.postForObject(url, new HttpEntity <String>(httpHeaders), String.class);
            System.out.println(result2);
        } else {
            System.out.println(result1);
        }

    }

}

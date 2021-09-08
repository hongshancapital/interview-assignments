package com.scdt.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class ShortUrlControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void longUrlFind() {
        ParameterizedTypeReference<String> type = new ParameterizedTypeReference<String>() {};
        ResponseEntity<String> result = restTemplate.exchange("/find?shortUrl=com", HttpMethod.GET, null, type);
        log.info("result:{}" + result.getBody());
    }

    @Test
    public void shortUrlCreate() {
        HttpHeaders requestHeaders = new HttpHeaders();
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("longUrl", "http://www.baidu.com");
        HttpEntity<MultiValueMap> requestEntity = new HttpEntity<MultiValueMap>(requestBody, requestHeaders);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("/create", requestEntity, String.class);
        log.info("responseEntity:{}" + responseEntity.getBody());
    }
}

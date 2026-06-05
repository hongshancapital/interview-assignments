package com.skg.domain.demo.controller;

import com.skg.domain.demo.ShortDomainDempApplication;
import com.skg.domain.demo.param.LongDomainParam;
import com.skg.domain.demo.param.ShortDomainParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author smith skg
 * @Date 2021/10/11 17:27
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShortDomainDempApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DomainControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testShortDomain() {
        LongDomainParam body = new LongDomainParam();
        body.setLongDomain("fasdfasdfasdf2gsdfsacdfas234");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<LongDomainParam> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> resultBody = restTemplate.exchange("/api/v1.1/get-short-domain", HttpMethod.POST,
                requestEntity, String.class);
        System.out.println(resultBody.getBody());
    }

    @Test
    public void testShortDomainV2() {
        LongDomainParam body = new LongDomainParam();
        body.setLongDomain("hdfsdfasdfasdfasdfa");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<LongDomainParam> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> resultBody = restTemplate.exchange("/api/v1.2/get-short-domain", HttpMethod.POST,
                requestEntity, String.class);
        System.out.println(resultBody.getBody());
    }

    @Test
    public void testLongDomain() {
        ShortDomainParam body = new ShortDomainParam();
        body.setShortDomain("t8ITkQ");
        HttpHeaders headers = new HttpHeaders();

        HttpEntity<ShortDomainParam> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> resultBody = restTemplate.exchange("/api/get-long-domain", HttpMethod.POST,
                requestEntity, String.class);
        System.out.println(resultBody.getBody());
    }
}

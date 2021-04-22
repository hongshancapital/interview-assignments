package com.example.domain.controller;

import com.example.domain.Application;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DomainControllerTest {

    @LocalServerPort
    private Integer port;

    private RestTemplate restTemplate = new RestTemplate();

    private static final HttpHeaders HTTP_HEADERS = new HttpHeaders();

    static {
        HTTP_HEADERS.add("Content-Type", MediaType.TEXT_PLAIN_VALUE);
    }

    /**
     * 测试保存
     */
    @Test
    @Order(1)
    public void testSave() {
        final ResponseEntity<String> response = doSave();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

    private ResponseEntity<String> doSave() {
        String url = String.format("http://localhost:%d/domains", port);
        HttpEntity<String> entity = new HttpEntity<>("www.leichi.com", HTTP_HEADERS);
        return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    }

    /**
     * 测试读取，依赖testSave
     */
    @Test
    @Order(2)
    public void testRead() {
        String url = String.format("http://localhost:%d/domains?shortDomain=00000001", port);
        HttpEntity<String> entity = new HttpEntity<>(HTTP_HEADERS);
        final ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("www.leichi.com", response.getBody());
    }

    /**
     * 测试异常，会返回e.getMessage
     */
    @Test
    @Order(3)
    public void testException() {
        final ResponseEntity<String> response = doSave();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("长域名已存在", response.getBody());
    }

}

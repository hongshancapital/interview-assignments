package com.scdt.controller;

import com.scdt.ShortUrlApplication;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Controller测试类
 *
 * @author penghai
 * @date 2022/5/3
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShortUrlApplication.class )
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ShortUrlControllerTest {

    @Autowired
    private ShortUrlController shortUrlController;

    /**
     * 输入长域名，返回短域名
     */
    @Test
    @Order(1)
    void givenLongUrlShouldReturnShortUrl() {
        ResponseEntity<String> response = shortUrlController.getShortUrl("https://www.baidu.com/");
        assertThat(response.getBody()).isEqualTo("gNdH");
    }

    /**
     * 输入为空时，返回内部错误
     */
    @Test
    @Order(2)
    void givenNullLongUrlShouldReturnInternalServerError() {
        ResponseEntity<String> response = shortUrlController.getShortUrl("");
        System.out.println(response);
        assertThat(response.getStatusCodeValue()).isEqualTo(500);
    }

    /**
     * 输入非法长域名时，返回内部错误
     */
    @Test
    @Order(3)
    void givenInvalidLongUrlShouldReturnInternalServerError() {
        ResponseEntity<String> response = shortUrlController.getShortUrl("abcde");
        System.out.println(response);
        assertThat(response.getStatusCodeValue()).isEqualTo(500);
    }

    /**
     * 输入短域名时，返回长域名
     */
    @Test
    @Order(4)
    void givenShortUrlShouldReturnLongUrl() {
        ResponseEntity<String> response = shortUrlController.getLongUrl("gNdH");
        assertThat(response.getBody()).isEqualTo("https://www.baidu.com/");
    }

    /**
     * 输入为空时，返回内部错误
     */
    @Test
    @Order(5)
    void givenNullShortUrlShouldReturnInternalServerError() {
        ResponseEntity<String> response = shortUrlController.getLongUrl("");
        assertThat(response.getStatusCodeValue()).isEqualTo(500);
    }

    /**
     * 输入非法短域名时，返回内部错误
     */
    @Test
    @Order(6)
    void givenInvalidShortUrlShouldReturnNotFound() {
        ResponseEntity<String> response = shortUrlController.getLongUrl("abcdefghijk");
        assertThat(response.getStatusCodeValue()).isEqualTo(500);
    }

    /**
     * 不存在的短链接返回404异常
     */
    @Test
    @Order(7)
    void givenShortUrlShouldReturnNotFound() {
        ResponseEntity<String> response = shortUrlController.getLongUrl("abc");
        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }
}
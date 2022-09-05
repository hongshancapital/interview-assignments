package com.example.service.test.user;

import com.example.service.TokenService;
import com.example.service.test.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 描述:
 * 业务测试类
 *
 * @author eric
 * @create 2021-07-22 10:43 下午
 */
@Slf4j
@EnableAutoConfiguration
@SpringBootTest(classes = TokenServiceTest.class)
public class TokenServiceTest extends BaseTest {
    @Autowired
    TokenService tokenService;

    @Test
    public void createTokenTest() {

    }

    @Test
    public void checkTokenTest() {

    }
}

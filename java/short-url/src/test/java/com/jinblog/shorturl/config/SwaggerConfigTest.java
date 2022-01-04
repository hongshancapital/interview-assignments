package com.jinblog.shorturl.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springfox.documentation.spring.web.plugins.Docket;

import static org.junit.jupiter.api.Assertions.*;

// 指定profile
@Profile("default")
@SpringBootTest
// 使用spring的测试框架
@ExtendWith(SpringExtension.class)
class SwaggerConfigTest {

    private SwaggerConfig swaggerConfig;
    @BeforeEach
    public void before() {
        if (swaggerConfig == null) {
            swaggerConfig = new SwaggerConfig();
        }
    }

    @Test
    void createRestApi() {
        assertEquals(Docket.class, swaggerConfig.createRestApi().getClass());
    }
}
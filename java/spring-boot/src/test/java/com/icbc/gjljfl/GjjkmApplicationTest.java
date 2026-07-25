package com.icbc.gjljfl;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ServletComponentScan("com.icbc.gjjkm.config.*")
@ComponentScan("com.icbc.gjjkm.*")
@MapperScan("com.icbc.gjjkm.mapper.*")
class GjjkmApplicationTest {

    @Test
    public static void main(String[] args) {
        SpringApplication.run(GjljflApplication.class, args);
    }
}
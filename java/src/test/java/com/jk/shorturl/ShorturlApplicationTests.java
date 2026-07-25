package com.jk.shorturl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;


@Slf4j
@SpringBootTest
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
public class ShorturlApplicationTests {
    public static void main(String[] args){
        SpringApplication.run(ShorturlApplicationTests.class);
    }
}

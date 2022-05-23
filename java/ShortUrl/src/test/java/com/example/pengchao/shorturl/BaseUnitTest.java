package com.example.pengchao.shorturl;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = ShortUrlApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
public abstract class BaseUnitTest {

}

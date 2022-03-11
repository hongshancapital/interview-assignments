package com.mx.demo.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApiControllerTest {

    @Autowired
    private ApiController apiController;

    @Test
    void testExec(){
        Assertions.assertNotNull(apiController.exec("1"));
    }

    @Test
    void testError(){
        Assertions.assertNotNull(apiController.error("1"));
    }
}

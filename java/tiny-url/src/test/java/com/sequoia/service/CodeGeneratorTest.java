package com.sequoia.service;

import com.sequoia.infrastructure.service.impl.CodeGeneratorImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * Descript:
 * File: com.sequoia.service.CodeGeneratorTest
 * Author: daishengkai
 * Date: 2022/3/30 23:07
 * Copyright (c) 2022,All Rights Reserved.
 */
@Slf4j
@SpringBootTest
public class CodeGeneratorTest {

    @Resource
    private CodeGeneratorImpl codeGenerator;

    @Test
    public void generateTinyCodeTest() {
        log.error(codeGenerator.generateTinyCode("test.sequence.com"));
    }

    @Test
    public void testGenerateTinyCodeMore() {
        for (int i = 5000; i < 5100; i++) {
            log.error(codeGenerator.generateTinyCode(i+""));
        }
    }
}

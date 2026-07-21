package org.goofly.shortdomain.service.impl;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest()
public class LocalGeneratorServiceImplTests {
    @Resource
    private LocalGeneratorServiceImpl generatorService;

    @Test
    public void testGenerateCode() {
        for (int i = 0; i < 100; i++) {
            Assert.assertNotNull(generatorService.generateCode());
        }
    }
}

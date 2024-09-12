package com.shortlink.handler;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NumberGeneratorServiceTest {

    @Autowired
    private NumberGeneratorService numberGeneratorService;

    @Test
    public void testNextId() {
        Assert.assertNotNull(numberGeneratorService.nextId());
    }
}

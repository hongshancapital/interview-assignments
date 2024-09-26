package com.example.shorturl.service.generator.genstrategy;

import com.example.shorturl.ShorturlApplicationTest;
import com.example.shorturl.service.dto.GenerateEnum;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

public class IncrementGeneratorStrategyTest extends ShorturlApplicationTest {
    @Resource
    IncrementGeneratorStrategy incrementGeneratorStrategy;

    @Test
    public void support() {
        GenerateEnum support = incrementGeneratorStrategy.support();
        Assert.assertEquals(GenerateEnum.INCREMENT_GENERATOR, support);
    }
}
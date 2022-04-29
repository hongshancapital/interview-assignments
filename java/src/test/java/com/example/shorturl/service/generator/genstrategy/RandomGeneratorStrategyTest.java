package com.example.shorturl.service.generator.genstrategy;

import com.example.shorturl.ShorturlApplicationTest;
import com.example.shorturl.service.dto.GenerateEnum;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;


public class RandomGeneratorStrategyTest extends ShorturlApplicationTest {
    @Resource
    RandomGeneratorStrategy randomGeneratorStrategy;

    @Test
    public void support() {
        GenerateEnum support = randomGeneratorStrategy.support();
        Assert.assertEquals(GenerateEnum.RANDOM_GENERATOR, support);
    }
}
package com.example.shorturl.service.generator;

import com.example.shorturl.ShorturlApplicationTest;
import com.example.shorturl.service.generator.genstrategy.IGeneratorStrategy;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

public class RandomGenerateFactoryTest extends ShorturlApplicationTest {
    @Resource
    RandomGenerateFactory randomGenerateFactory;

    @Test
    public void getGenerator() {
        IGeneratorStrategy generator = randomGenerateFactory.getGenerator();
        System.out.println("RandomGenerateFactoryTest:getGenerator");
        Assert.assertNotNull(generator);
    }
}
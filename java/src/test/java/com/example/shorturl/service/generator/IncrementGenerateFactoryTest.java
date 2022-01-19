package com.example.shorturl.service.generator;

import com.example.shorturl.ShorturlApplicationTest;
import com.example.shorturl.service.generator.genstrategy.IGeneratorStrategy;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

public class IncrementGenerateFactoryTest extends ShorturlApplicationTest {
    @Resource
    IncrementGenerateFactory incrementGenerateFactory;

    @Test
    public void getGenerator() {
        IGeneratorStrategy generator = incrementGenerateFactory.getGenerator();
        System.out.println("IncrementGenerateFactoryTest:getGenerator");
        Assert.assertNotNull(generator);
    }
}
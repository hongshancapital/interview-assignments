package com.example.shorturl.service.generator.genstrategy;

import com.example.shorturl.config.exception.BizException;
import com.example.shorturl.service.dto.GenerateEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.ArrayBlockingQueue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DefaultGenerateStrategyTest {
    @Resource
    IncrementGeneratorStrategy incrementGeneratorStrategy;
    @Resource
    RandomGeneratorStrategy randomGeneratorStrategy;

    @Test
    public void incrementGenerateCode() {
        GenerateEnum support = incrementGeneratorStrategy.support();
        Assert.assertEquals(GenerateEnum.INCREMENT_GENERATOR, support);
        incrementGeneratorStrategy.generateCode();
    }

    @Test(expected = BizException.class)
    public void testIncrementException() {
        IncrementGeneratorStrategy.GenIncrementNumber genIncrementNumber = incrementGeneratorStrategy.new GenIncrementNumber(Long.MAX_VALUE, new ArrayBlockingQueue<Long>(100));
        genIncrementNumber.run();
    }

    @Test
    public void randomGenerateCode() {
        GenerateEnum support = randomGeneratorStrategy.support();
        Assert.assertEquals(GenerateEnum.RANDOM_GENERATOR, support);
        randomGeneratorStrategy.generateCode();
    }

//    @Test(expected = BizException.class)
//    public void testRandomException() {
//        RandomGeneratorStrategy.GenRandomNumber randomNumber = randomGeneratorStrategy.new GenRandomNumber(new ArrayBlockingQueue<Long>(100));
//        randomNumber.run();
//    }
}
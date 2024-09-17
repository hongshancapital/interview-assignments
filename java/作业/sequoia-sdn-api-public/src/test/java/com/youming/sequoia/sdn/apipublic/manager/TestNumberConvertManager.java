package com.youming.sequoia.sdn.apipublic.manager;

import com.youming.sequoia.sdn.apipublic.config.SpringApplicationConfig;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringApplicationConfig.class)
public class TestNumberConvertManager {

    @Autowired
    private NumberConvertManager numberConvertManager;


    @Test
    public void test() {
        Random random = new Random();
        long input = random.nextInt(1000000);
        int seed = 62;
        String parseOtherNumber = numberConvertManager.toOtherNumberSystem(input, seed);
        long parseTenNumber = numberConvertManager.toDecimalNumber(parseOtherNumber, 62);
        assertEquals(parseTenNumber, input);

        //测试10进制分支
        numberConvertManager.toDecimalNumber(String.valueOf(input), 10);
        //测试小于0的分支
        numberConvertManager.toOtherNumberSystem(-1, seed);
    }
}

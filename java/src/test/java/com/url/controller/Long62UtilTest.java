package com.url.controller;

import com.url.Application;
import com.url.utils.Long62Util;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 数字转62进制测试类
 * @ClassName Long62UtilTest
 * @Author abc
 * @Date 2022/4/30
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Slf4j
public class Long62UtilTest {

    private final static long longValue = 35174605;

    private final static String longStr = "2nAwl";

    @Test
    public void to62StringTest(){
        String res = Long62Util.to62String(longValue);
        if(res.equals(longStr)){
            log.info("to 62 string test ok!");
        }
    }

    @Test
    public void longValueTest(){
        long long62Value = Long62Util.longValue(longStr);
        if(longValue == long62Value){
            log.info("long str to long value test ok!");
        }
    }
}
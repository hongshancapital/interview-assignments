package com.example.service.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 测试基类
 * @author eric
 * @date 2020/7/22 22:23
 */
@RunWith(SpringRunner.class)
@ComponentScan("com.example")
public class BaseTest {
    @Test
    public void doNothing() {

    }
}

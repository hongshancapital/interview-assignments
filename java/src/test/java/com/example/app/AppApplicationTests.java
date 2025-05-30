package com.example.app;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class AppApplicationTests {

    public static final Logger log = LoggerFactory.getLogger(AppApplicationTests.class);

    @Before
    public void init() {
        log.info("测试开始 >>>>");
    }

    @After
    public void after() {
        log.info("测试结束 <<<<");
    }

    @Test
    public void test() {

    }
}

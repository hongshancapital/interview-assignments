package org.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootUnitTest {
    @Before
    public void init(){
        System.out.println("测试开始");
    }

    @After
    public void after(){
        System.out.println("测试结束");
    }
}

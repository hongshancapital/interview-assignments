package com;

import com.liuxiang.Application;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author liuxiang6
 * @date 2022-01-07
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class BaseTest {
    private long time;

    @Before
    public void setUp() {
        time = System.currentTimeMillis();
        System.out.println("开始时间：" + time);
    }

    @After
    public void tearDown() {
        System.out.println("结束时间：" + System.currentTimeMillis() );
        System.out.println("耗时：" + (System.currentTimeMillis() - time));
    }
}

package com.polly.shorturl.tools;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author polly
 * @date 2022.03.24 15:54:53
 */
@SpringBootTest
public class SnowflakeIdWorkerTest {
    @Autowired
    private SnowflakeIdWorker worker;

    @Test
    public void test01() {
        System.out.println(worker.nextId());
    }
}

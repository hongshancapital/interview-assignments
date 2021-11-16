package com.liu.shorturl.utils;

import com.liu.shorturl.ShorturlApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Description： 雪花算法生成唯一ID测试
 * Author: liujiao
 * Date: Created in 2021/11/12 15:55
 * email: liujiao@fcbox.com
 * Version: 0.0.1
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ShorturlApplication.class)
public class SnowFlakeIdWorkerTest {

    @Test
    public void testNextId() {
        IdWorker worker = new SnowFlakeIdWorker(1, 1, 1);
        Assert.assertNotNull(worker.nextId());
    }

}
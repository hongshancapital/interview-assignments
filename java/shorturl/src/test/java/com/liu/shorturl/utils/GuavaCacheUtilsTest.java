package com.liu.shorturl.utils;

import com.liu.shorturl.ShorturlApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Description： guava cache jvm缓存工具类测试
 * Author: liujiao
 * Date: Created in 2021/11/12 15:59
 * email: liujiao@fcbox.com
 * Version: 0.0.1
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShorturlApplication.class)
public class GuavaCacheUtilsTest {

    @Test
    public void test() {
        String key = "test1";
        String value = "value1";
        GuavaCacheUtils.put(key, value);

        String res = (String) GuavaCacheUtils.get(key);

        Assert.assertEquals(res, value);
    }

}
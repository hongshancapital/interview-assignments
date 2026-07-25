package com.liu.shorturl.utils;

import com.liu.shorturl.ShorturlApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Description： 进制转换工具类测试
 * Author: liujiao
 * Date: Created in 2021/11/12 15:57
 * email: liujiao@fcbox.com
 * Version: 0.0.1
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ShorturlApplication.class)
public class NumericConvertUtilsTest {

    @Test
    public void test() {
        long number = 12189343;
        Assert.assertNotNull(NumericConvertUtils.toOtherNumberSystem(number, 62));
        number = -10;
        Assert.assertNotNull(NumericConvertUtils.toOtherNumberSystem(number, 62));
    }

}
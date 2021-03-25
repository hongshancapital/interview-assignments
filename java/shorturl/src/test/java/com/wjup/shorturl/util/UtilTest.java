package com.wjup.shorturl.util;

import com.wjup.shorturl.ShorturlApplication;
import com.wjup.shorturl.config.TestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class) // 等价于使用 @RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { ShorturlApplication.class, TestConfig.class })
@Slf4j
public class UtilTest {


    /**
     * 测试生产随机字符串
     */
    @Test
    public void testGetStringRandom(){
        String stringRandom = ShortUtils.getStringRandom(8);
        Assert.assertNull(stringRandom);
        Assert.assertEquals(8,stringRandom.length());
    }

    /**
     * 测试生成短url的工具类  62进制转换
     */
    public void testTo62url(){
        String s = ShortUtils.to62url("https://github.com/scdt-china/interview-assignments/tree/master/java");
        Assert.assertNull(s);
    }

}

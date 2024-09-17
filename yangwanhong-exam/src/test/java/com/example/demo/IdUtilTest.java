package com.example.demo;

import com.example.demo.util.EncodeUtil;
import com.example.demo.util.IdUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

@SpringBootTest
@RunWith(SpringRunner.class)
public class IdUtilTest {

    @Test
    public void getGlobalIdTest() {
        for (int i = 0; i < 100; i++) {
            Long id = IdUtil.getGlobalId();
            Assert.assertTrue("生成失败", null != id);
        }
    }
}

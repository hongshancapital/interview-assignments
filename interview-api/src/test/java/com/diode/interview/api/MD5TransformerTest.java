package com.diode.interview.api;

import com.diode.interview.domain.entity.MyURL;
import com.diode.interview.infrastructure.ability.transformer.MD5Transformer;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author unlikeha@163.com
 * @date 2022/4/29
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class MD5TransformerTest {
    @Resource
    private MD5Transformer transformer;

    @Test
    public void testTransform(){
        String shortURL = transformer.transform("https://github.com/diodeme/interview-assignments/tree/master/java");
        Assert.assertTrue(MyURL.isShortURL(shortURL));

        String shortURL1 = transformer.transform("a");
        Assert.assertTrue(MyURL.isShortURL(shortURL1));
    }
}

package com.shortlink.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConvertRequestUtilTest {

    @Test
    public void testConvertUrl() {

        Assert.assertNotNull(ConvertRequestUtil.createShortLinkRequest("test","www.baidu.com",0));
        Assert.assertNotNull(ConvertRequestUtil.fetchOriginalUrlRequest("test","www.baidu.com",0));
    }
}

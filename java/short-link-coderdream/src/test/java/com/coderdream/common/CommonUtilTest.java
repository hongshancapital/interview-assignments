package com.coderdream.common;

import com.coderdream.utils.CommonUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommonUtilTest {

    @Test
    public void testMurmurHash32() {
        Assert.assertEquals(0, CommonUtil.murmurHash32(null));
        Assert.assertEquals(0, CommonUtil.murmurHash32(""));
        Assert.assertEquals(692015166, CommonUtil.murmurHash32( "http://www.baidu.com/IVhh6MKdNwcWT7qxS-883543"));
    }

    @Test
    public void testGenerateZeroString() {
        Assert.assertEquals("", CommonUtil.generateZeroString(-1));
        Assert.assertEquals("", CommonUtil.generateZeroString(0));
        Assert.assertEquals("0", CommonUtil.generateZeroString(1));
        Assert.assertEquals("00", CommonUtil.generateZeroString(2));
    }
}
package com.duoji.shortlink.common;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author XY
 * @Description
 * @createTime 2021年12月18日 22:03:00
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ConvertorUtilTest {

    @Test(expected = Exception.class)
    public void  testConvertUtil(){
        Assert.assertEquals(71,ConvertorUtil.decodeScaleTo10("19",62));
        Assert.assertEquals("0019",ConvertorUtil.encode10ToScale(71,4,62));
        ConvertorUtil.decodeScaleTo10(null,13);

    }

    @Test(expected = IllegalArgumentException.class)
    public void  testConvertUtil1(){
        ConvertorUtil.decodeScaleTo10("@",13);
    }

    @Test(expected = IllegalArgumentException.class)
    public void  testConvertUtil2(){
        ConvertorUtil.encode10ToScale(-10,13);
    }
}

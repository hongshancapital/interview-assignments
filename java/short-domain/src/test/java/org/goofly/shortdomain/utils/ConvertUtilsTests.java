package org.goofly.shortdomain.utils;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConvertUtilsTests {

    @Test
    public void testEnCode() {
        long maxNum = 10086l;
        long minNum = 60l;
        Assert.assertEquals("2CG", ConvertUtils.encode(maxNum));
        Assert.assertEquals("Y", ConvertUtils.encode(minNum));

        Assert.assertEquals(maxNum, ConvertUtils.decode("2CG"));
        Assert.assertEquals(minNum, ConvertUtils.decode("Y"));
        Assert.assertThrows(NullPointerException.class, () -> ConvertUtils.decode(null));
        Assert.assertThrows(StringIndexOutOfBoundsException.class, () -> ConvertUtils.encode(-1l));

    }


    @Test
    public void encode() {

        Assert.assertNotNull(ConvertUtils.encode(1));
        Assert.assertNotNull(ConvertUtils.encode(111));
        Assert.assertNotNull(ConvertUtils.encode(111111));
        Assert.assertNotNull(ConvertUtils.encode(1111111111));
    }

    @Test
    public void testGetMaxValue() {
        Assert.assertThrows(IllegalArgumentException.class, () -> ConvertUtils.getMaxValInBit(0));
        Assert.assertThrows(Exception.class, () -> ConvertUtils.getMaxValInBit(-1));

        Assert.assertEquals(218340105584895L, ConvertUtils.getMaxValInBit(8));
    }

}

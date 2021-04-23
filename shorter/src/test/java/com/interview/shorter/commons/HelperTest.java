package com.interview.shorter.commons;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Bai Lijun mailTo: 13910160159@163.com
 * Created at 2021-04-23
 */
@SpringBootTest
public class HelperTest {


    @Test
    public void testHelperParam() {
        Assert.assertNull(Helper.hash(null));
    }

    public final static char[] digits = {
            '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b',
            'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z'
    };

    @Test
    public void testRandomStr() {
        String rlt = Helper.random(6, Helper.digits);
        System.out.println(rlt);
        Assert.assertEquals(6, rlt.length());
    }

    @Test
    public void testToString() {
        String r = Helper.toString(1L, 2);
        Assert.assertEquals("1", r);
        String r2 = Helper.toString(32, 10);
        Assert.assertEquals("32", r2);

        String r3 = Helper.toString(32, 1);
        Assert.assertEquals("32", r3);
    }

    @Test
    public void testToString2() {
        String r = Helper.toString(-1, 2);
        Assert.assertEquals("-1", r);

        String r2 = Helper.toString(-1, 65);
        Assert.assertEquals("-1", r2);


    }
}

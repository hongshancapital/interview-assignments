package com.bingl.web.util;

import com.bingl.web.ApplicationTestBase;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class ByteUtilTest extends ApplicationTestBase {

    @Test
    public void intToBytesHighHead(){
        Integer value=210398822;

        byte [] bytes=ByteUtil.intToBytesHighHead(value);

        System.out.println(Arrays.toString(bytes));

        Assert.assertArrayEquals(new byte[]{12, -118, 110, 102},bytes);
    }

    @Test
    public void byteToIntHighHead(){
        byte [] bytes=new byte[]{12, -118, 110, 102};

        Integer value=ByteUtil.byteToIntHighHead(bytes,0);
        Integer value1=210398822;
        Assert.assertEquals(value1,value);
    }
}

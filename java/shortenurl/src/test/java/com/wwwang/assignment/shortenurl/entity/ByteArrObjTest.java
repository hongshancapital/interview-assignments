package com.wwwang.assignment.shortenurl.entity;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import scala.util.control.Exception;

public class ByteArrObjTest {

    @Test
    public void test(){
        byte[] a= {1,2,3,4};
        byte[] b= {1,2,3,4};
        ByteArrObj obj1= new ByteArrObj(a);
        ByteArrObj obj2 = new ByteArrObj(b);
        Assert.assertNotEquals(a,b);
        Assert.assertEquals(obj1.hashCode(),obj2.hashCode());
        Assert.assertEquals(obj1,obj2);

        Assert.assertNotEquals(true,obj1.equals("test"));
        Assert.assertEquals(true,obj1.equals(obj1));

    }
}

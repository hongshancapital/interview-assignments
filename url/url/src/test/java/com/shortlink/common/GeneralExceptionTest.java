package com.shortlink.common;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GeneralExceptionTest {

    @Test
    public void testGeneralException() {

        Assert.assertEquals(new GeneralException(Status.SUCCESS).getCode(), new Integer(0));
        Assert.assertEquals(new GeneralException(Status.SUCCESS).getDesc(), "成功");
    }
}

package com.shortlink.common;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ResultTest {

    @Test
    public void testResult() {
        Assert.assertEquals(new Integer(0), ResponseBuilder.buildSuccess("").getCode());
        Assert.assertEquals(new Integer(1001), ResponseBuilder.bulidError(Status.INVALID_PARAM.getCode(), Status.INVALID_RESULT.getDesc()).getCode());
        Assert.assertEquals("成功", ResponseBuilder.buildSuccess("").getMsg());
        Assert.assertNotNull(ResponseBuilder.bulidError(new GeneralException(Status.INVALID_RESULT)));
    }
}

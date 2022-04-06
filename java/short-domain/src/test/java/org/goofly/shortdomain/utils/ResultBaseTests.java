package org.goofly.shortdomain.utils;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ResultBaseTests {

    @Test
    public void  testConvertUtil(){
        ResultBase<Object> objectResultBase = new ResultBase<>();
        Assert.assertTrue(ResultBase.successRsp(null).isSuccess());
        Assert.assertFalse(objectResultBase.errorRsp("xxx").isSuccess());
    }

}

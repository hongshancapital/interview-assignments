package com.coderdream.common;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ResultTest {

    @Test
    public void testResult() {
        Assert.assertEquals("Failure", ResultBuilder.buildFailure("Failure").getCode());
        Assert.assertEquals("200", ResultBuilder.buildFailure("200", "").getCode());
        Assert.assertEquals(true, ResultBuilder.buildSuccess().getSuccess());
        Assert.assertEquals("success", ResultBuilder.buildSuccess("success").getData());
        Assert.assertEquals(0, ResultBuilder.buildSuccessList(new ArrayList<>()).getData().size());
    }
}
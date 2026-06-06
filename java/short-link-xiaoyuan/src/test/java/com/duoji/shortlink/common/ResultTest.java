package com.duoji.shortlink.common;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

/**
 * @author XY
 * @Description
 * @createTime 2021年12月19日 13:11:00
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ResultTest {

    @Test
    public void testResult(){
        Assert.assertEquals("fail",ResultBuilder.buildFailed("fail").getCode());
        Assert.assertEquals("200",ResultBuilder.buildFailed("200","").getCode());
        Assert.assertEquals(true,ResultBuilder.buildSuccess().isSuccess());
        Assert.assertEquals("success",ResultBuilder.buildSuccess("success").getData());
        Assert.assertEquals(0,ResultBuilder.buildSuccessList(new ArrayList<>()).getData().size());
    }
}

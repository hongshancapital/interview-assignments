package com.hongshang.common;

import mockit.Mocked;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

/**
 * 服务通用返回值类型
 *
 * @author kobe
 * @date 2021/12/19
 */
@RunWith(JMockit.class)
public class ComResultTest {

    @Tested
    ComResult comResult;


    @Test
    public void testSUCCESS() {
        comResult.SUCCESS("SUCCESS");
    }

    @Test
    public void FAIL() {
        comResult.FAIL("FAIL");
    }


    @Test
    public void getCode() {
        comResult.getCode();
    }

    @Test
    public void getMessage() {
        comResult.getMessage();
    }

    @Test
    public void getIsSuccess() {
        comResult.getIsSuccess();
    }

    @Test
    public void getResult() {
        comResult.getResult();
    }

    @Test
    public void setCode() {
        comResult.setCode(200);
    }

    @Test
    public void setMessage() {
        comResult.setMessage("fail");
    }

    @Test
    public void setIsSuccess() {
        comResult.setIsSuccess(true);
    }

    @Test
    public void setResult() {
        comResult.setResult(true);
    }

    @Test
    public void testEquals() {
        comResult.equals(true);
    }

    @Test
    public void canEqual() {
        comResult.canEqual(true);
    }

    @Test
    public void testHashCode() {
        comResult.hashCode();
    }

    @Test
    public void testToString() {
        comResult.toString();
    }

    @Test
    public void testCanEqual() {
        comResult.canEqual(new ComResult());
    }


}
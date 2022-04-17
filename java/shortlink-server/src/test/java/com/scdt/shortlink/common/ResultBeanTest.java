package com.scdt.shortlink.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author xbhong
 * @date 2022/4/17 0:34
 */
public class ResultBeanTest {

    private static final int SUCCESS_CODE = 200;
    private static final int FAIL_CODE = 500;


    @Test
    public void resultBeanTest_1() {
        ResultBean resultBean = new ResultBean();
        resultBean.setCode(200);
        resultBean.setMsg("操作成功");
        resultBean.setData("http://t.cn/F17SDE");
        Assertions.assertEquals(resultBean.getCode(), 200);
    }

    @Test
    public void resultBeanTest_2() {
        ResultBean resultBean = new ResultBean(400, "操作成功", "");
        Assertions.assertEquals(resultBean.getCode(), 400);
    }

    @Test
    public void resultSuccessTest() {
        ResultBean resultBean = ResultBean.success("操作成功","http://t.cn/F17SDE");
        Assertions.assertEquals(resultBean.getCode(), SUCCESS_CODE);
    }

    @Test
    public void resultFailTest() {
        ResultBean resultBean = ResultBean.fail("操作失败",null);
        Assertions.assertEquals(resultBean.getCode(), FAIL_CODE);
    }

    @Test
    public void resultCreateTest() {
        ResultBean resultBean = ResultBean.create(400,"域名格式不正确","https://github.com/scdt-china/interview-assignments/tree/master/java");
        Assertions.assertEquals(resultBean.getCode(), 400);
    }


}

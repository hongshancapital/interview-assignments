package com.domain.urlshortener.model;

import com.domain.urlshortener.enums.BizStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author: rocky.hu
 * @date: 2022/4/6 2:56
 */
public class ResponseModelTest {

    @Test
    public void test_constructor_1() {
        ResponseModel<Object> objectResponseModel = new ResponseModel<>(BizStatus.SUCCESS, null);
        Assertions.assertEquals(objectResponseModel.getCode(), BizStatus.SUCCESS.getCode());
    }

    @Test
    public void test_constructor_2() {
        ResponseModel<Object> objectResponseModel = new ResponseModel<>(BizStatus.SUCCESS);
        Assertions.assertEquals(objectResponseModel.getCode(), BizStatus.SUCCESS.getCode());
    }

    @Test
    public void test_constructor_3() {
        ResponseModel<Object> objectResponseModel = new ResponseModel<>("a", "b");
        Assertions.assertEquals(objectResponseModel.getCode(), "a");
        Assertions.assertEquals(objectResponseModel.getMessage(), "b");
    }

    @Test
    public void test_constructor_4() {
        ResponseModel<Object> objectResponseModel = new ResponseModel<>("a", "b", null);
        Assertions.assertEquals(objectResponseModel.getCode(), "a");
        Assertions.assertEquals(objectResponseModel.getMessage(), "b");
        Assertions.assertEquals(objectResponseModel.getData(), null);
    }

    @Test
    public void test_constructor_5() {
        ResponseModel<Object> objectResponseModel = new ResponseModel<>();
        Assertions.assertEquals(objectResponseModel.getCode(), null);
        Assertions.assertEquals(objectResponseModel.getMessage(), null);
    }

    @Test
    public void test_fail_1() {
        ResponseModel fail = ResponseModel.fail();
        Assertions.assertEquals(fail.getCode(), BizStatus.FAILED.getCode());
    }

    @Test
    public void test_fail_2() {
        ResponseModel fail = ResponseModel.fail(BizStatus.FAILED, "a");
        Assertions.assertEquals(fail.getCode(), BizStatus.FAILED.getCode());
        Assertions.assertEquals(fail.getMessage(), "a");
    }

    @Test
    public void test_fail_3() {
        ResponseModel fail = ResponseModel.fail(BizStatus.FAILED);
        Assertions.assertEquals(fail.getCode(), BizStatus.FAILED.getCode());
    }

    @Test
    public void test_fail_4() {
        ResponseModel fail = ResponseModel.fail("A");
        Assertions.assertEquals(fail.getData(), "A");
    }

    @Test
    public void test_ok_1() {
        ResponseModel fail = ResponseModel.ok();
        Assertions.assertEquals(fail.getCode(), BizStatus.SUCCESS.getCode());
    }

    @Test
    public void test_ok_2() {
        ResponseModel fail = ResponseModel.ok("A");
        Assertions.assertEquals(fail.getData(), "A");
    }

    @Test
    public void test_getCode() {
        ResponseModel fail = ResponseModel.fail(BizStatus.FAILED, "a");
        Assertions.assertEquals(fail.getCode(), BizStatus.FAILED.getCode());
    }

    @Test
    public void test_getMessage() {
        ResponseModel fail = ResponseModel.fail(BizStatus.FAILED, "a");
        Assertions.assertEquals(fail.getMessage(), "a");
    }

    @Test
    public void test_getData() {
        ResponseModel fail = ResponseModel.ok("A");
        Assertions.assertEquals(fail.getData(), "A");
    }

}

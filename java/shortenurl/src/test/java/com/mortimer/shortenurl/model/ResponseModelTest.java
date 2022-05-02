package com.mortimer.shortenurl.model;

import com.mortimer.shortenurl.enums.ResponseStatusEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ResponseModelTest {
    @Test
    public void test_constructor() {
        ResponseModel model = new ResponseModel(ResponseStatusEnum.ERROR, null);
        Assertions.assertEquals(model.getCode(), ResponseStatusEnum.ERROR.getCode());
        Assertions.assertEquals(model.getMessage(), ResponseStatusEnum.ERROR.getMessage());
    }

    @Test
    public void test_success() {
        ResponseModel model = ResponseModel.success("ok");
        Assertions.assertEquals(model.getData(), "ok");
    }
}

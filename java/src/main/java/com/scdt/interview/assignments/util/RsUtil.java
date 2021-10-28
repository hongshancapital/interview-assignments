package com.scdt.interview.assignments.util;

import com.scdt.interview.assignments.bean.base.ResultEntity;
import com.scdt.interview.assignments.constants.RsConsts;

public class RsUtil {
    public static ResultEntity success(Object data) {
        return createResponseVO(data, "操作成功", RsConsts.SUCCESS_STATUS);
    }

    public static ResultEntity success(Object data, String message) {
        return createResponseVO(data, message, RsConsts.SUCCESS_STATUS);
    }

    public static ResultEntity error() {
        return createResponseVO(new Object(), "", RsConsts.ERROR_STATUS);
    }

    public static ResultEntity error(Object data, String errorCode, String errorMsg) {
        return createResponseVO(data, errorMsg, RsConsts.ERROR_STATUS);
    }


    private static ResultEntity createResponseVO(Object data, String message, String code) {
        return ResultEntity.builder().data(data).code(code).message(message).build();
    }
}
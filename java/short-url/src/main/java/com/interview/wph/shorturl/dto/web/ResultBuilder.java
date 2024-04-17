package com.interview.wph.shorturl.dto.web;

import com.interview.wph.shorturl.common.consts.ConnConst;
import com.interview.wph.shorturl.dto.ResponseDto;

public class ResultBuilder<T> {

    public static <T> ResponseDto<T> success(T result) {
        return success(ConnConst.SUCCESS_CODE, result);
    }

    public static <T> ResponseDto<T> success(Integer code, T result) {
        return new ResponseDto<>(code, ConnConst.SUCCESS, result);
    }


    public static <T> ResponseDto<T> fail(Integer errorCode, String msg, T result) {
        return new ResponseDto<>(errorCode, msg, result);
    }

    public static <T> ResponseDto<T> fail(Integer code, String msg) {
        return fail(code, msg, null);
    }
}

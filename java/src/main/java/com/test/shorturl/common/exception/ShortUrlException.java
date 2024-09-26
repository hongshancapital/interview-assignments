package com.test.shorturl.common.exception;

import com.test.shorturl.common.result.ResultCodeEnum;

public class ShortUrlException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    protected String errorCode;

    public ShortUrlException(ResultCodeEnum resultCodeEnum,boolean writableStackTrace) {
        super(resultCodeEnum.message(),null,true,writableStackTrace);
        this.errorCode = resultCodeEnum.code();
    }

    public String getErrorCode() {
        return errorCode;
    }


}

package com.eagle.shorturl.exception;

import com.eagle.shorturl.result.ResultCodeEnum;
import lombok.Getter;

/**
 * @author eagle
 * @description
 */
public class BussinessException extends RuntimeException {

    @Getter
    private ResultCodeEnum resultCode;

    public BussinessException(ResultCodeEnum resultCode) {
        this(resultCode, resultCode.getMessage());
    }

    public BussinessException(ResultCodeEnum resultCode, String message) {
        super(message);
        this.resultCode = resultCode;
    }
}

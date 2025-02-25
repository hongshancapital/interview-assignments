package com.scdtchina.interview.exception;

import com.scdtchina.interview.enums.ErrorCodeEnum;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{
    private int errorCode;
    private String errorMsg;
    public BusinessException(ErrorCodeEnum errorCodeEnum) {
        this.errorCode = errorCodeEnum.getCode();
        this.errorMsg = errorCodeEnum.getErrorMsg();
    }
}

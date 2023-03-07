package com.scdt.shortenurl.common.exception;

import com.scdt.shortenurl.common.enums.ErrorCodeEnum;

/**
 * @Description 业务异常
 * @Author chenlipeng
 * @Date 2022/3/7 2:14 下午
 */
public class BizException extends RuntimeException {

    /**
     * 错误码
     */
    private ErrorCodeEnum errorCode;

    public BizException(ErrorCodeEnum errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BizException(String message) {
        super(message);
        this.errorCode = ErrorCodeEnum.INTERNAL_SYSTEM_ERROR;
    }

//    public BizException(ErrorCodeEnum errorCode, String errMsg) {
//        super(errMsg);
//        this.errorCode = errorCode;
//    }

//    public BizException(String message, Throwable cause) {
//        super(message, cause);
//        this.errorCode = ErrorCodeEnum.INTERNAL_SYSTEM_ERROR;
//    }

    public ErrorCodeEnum getErrorCode() {
        return errorCode;
    }
}

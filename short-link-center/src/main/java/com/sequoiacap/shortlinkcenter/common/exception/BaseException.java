package com.sequoiacap.shortlinkcenter.common.exception;

import com.sequoiacap.shortlinkcenter.common.enums.IErrorCode;

/**
 * @author xiuyuan
 * @date 2022/3/17
 */
public class BaseException extends BizException {

    protected String detailMsg;

    public BaseException(CommonErrorCodeEnum codeEnum, String detailMsg) {
        super(codeEnum.getMessage(), codeEnum.getCode());
        this.detailMsg = detailMsg;
    }

    public BaseException(int code, String message, Throwable e) {
        super(message, e, code);
    }

    public BaseException(int code, String message) {
        super(message, code);
    }

    public BaseException(int code, String message, String detailMsg) {
        super(message, code);
        this.detailMsg = detailMsg;
    }

    public BaseException(IErrorCode errorCode) {
        super(errorCode.getMessage(), errorCode.getCode());
    }

    public BaseException(IErrorCode errorCode, String message) {
        super(message, errorCode.getCode());
    }
}

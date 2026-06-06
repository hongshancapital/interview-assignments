package com.scdt.china.shorturl.common.exception;

import com.scdt.china.shorturl.common.GlobalErrorCode;

/**
 * @Author zhouchao
 * @Date 2021/11/26 14:51
 * @Description https://github.com/scdt-china/interview-assignments.git
 **/
public class BizException extends IllegalArgumentException {

    /**
     * serialVersionUID:
     *
     * @since JDK 1.8
     */
    private static final long serialVersionUID = 521707773001584847L;
    private GlobalErrorCode errorCode;

    public BizException(String msg) {
        super(msg);
    }

    public BizException(GlobalErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BizException(GlobalErrorCode errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }

    public GlobalErrorCode getErrorCode() {
        return errorCode;
    }

    public String code() {
        return errorCode.getCode();
    }

    public String message() {
        return errorCode.getMessage();
    }
}

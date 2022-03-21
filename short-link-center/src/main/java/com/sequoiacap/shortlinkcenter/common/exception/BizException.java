package com.sequoiacap.shortlinkcenter.common.exception;

/**
 * @author xiuyuan
 * @date 2022/3/17
 */
public class BizException extends RuntimeException {

    private static final long serialVersionUID = 2236201620230086989L;

    private int code;

    public BizException(int code) {
        this.code = code;
    }

    public BizException(String message, int code) {
        super(message);
        this.code = code;
    }

    public BizException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }

    public BizException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }

    public BizException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}

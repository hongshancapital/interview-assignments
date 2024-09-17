package com.wuaping.shortlink.exception;

/**
 * 业务异常
 *
 * @author Aping
 * @since 2022/3/19 14:34
 */
public class ServiceException extends RuntimeException {

    private static final ExceptionCodeMessage.CodeMessage DEFAULT = ExceptionCodeMessage.SERVICE_ERROR;

    private int errCode;

    public ServiceException(ExceptionCodeMessage.CodeMessage codeMessage) {
        super(codeMessage.getMessage());
        this.errCode = codeMessage.getCode();
    }

    public ServiceException(String errMsg) {
        super(errMsg);
        this.errCode = DEFAULT.getCode();
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }
}

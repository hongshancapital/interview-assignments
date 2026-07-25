package org.faof.exceptions;

public class BizException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    protected int errorCode;

    protected String errorMsg;

    public BizException(IApplicationException exception) {
        super(exception.getErrorMessage());
        errorCode = exception.getErrorCode();
        errorMsg = exception.getErrorMessage();
    }


    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public String getMessage() {
        return errorMsg;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

}

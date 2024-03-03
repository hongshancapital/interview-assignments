package com.tb.link.infrastructure.exception;

/**
 *
 * @author lhc
 * @date 2018/1/8
 */
public class TbRuntimeException extends RuntimeException {

    private String errorCode ;

    private String errorMsg ;

    public TbRuntimeException() {
        super();
    }

    public TbRuntimeException(String errorCode, String errorMsg) {
        super(errorCode);
        setErrInfo(errorCode,errorMsg);
    }

    public TbRuntimeException(String message) {
        super(message);
    }

    public TbRuntimeException(String message, String errorCode, String errorMsg) {
        super(message);
        setErrInfo(errorCode,errorMsg);
    }

    public TbRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public TbRuntimeException(String message, Throwable cause, String errorCode, String errorMsg) {
        super(message, cause);
        setErrInfo(errorCode,errorMsg);
    }

    public TbRuntimeException(Throwable cause) {
        super(cause);
    }

    public TbRuntimeException(Throwable cause, String errorCode, String errorMsg) {
        super(cause);
        setErrInfo(errorCode,errorMsg);
    }

    private void setErrInfo(String errorCode,String errorMsg){
        this.errorCode = errorCode ;
        this.errorMsg = errorMsg ;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}

package com.yilong.shorturl.common.exception;

import com.yilong.shorturl.common.enums.ErrorEnum;
import com.yilong.shorturl.model.Error;

public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 3678870756429234910L;

    private Integer status = 500;

    private Integer code = ErrorEnum.UNHANDLED_EXCEPTION.getErrorCode();

    private String message;

    public BusinessException(String message) {
        super(message);
        this.message = message;
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BusinessException(ErrorEnum errorEnum) {
        super(errorEnum.getErrorMsg());
        this.status = errorEnum.getHttpStatus();
        this.code = errorEnum.getErrorCode();
        this.message = errorEnum.getErrorMsg();
    }

    public BusinessException(ErrorEnum errorEnum, String supplement) {
        super(errorEnum.getErrorMsg());
        this.status = errorEnum.getHttpStatus();
        this.code = errorEnum.getErrorCode();
        this.message = errorEnum.getErrorMsg();
        if (supplement != null) {
            this.message = String.join(" ", this.message, supplement);
        }
    }

    public Error toError(){
        Error error = new Error();
        error.setCode(this.code);
        error.setMessage(this.message);
        return error;
    }
    public Integer getStatus() {return status;}

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

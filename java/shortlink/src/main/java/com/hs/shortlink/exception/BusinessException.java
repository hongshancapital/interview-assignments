package com.hs.shortlink.exception;

import com.hs.shortlink.domain.constant.ResultStatusEnum;

import java.util.Map;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private final ResultStatusEnum code;
    private Map<String, Object> data;

    public BusinessException(String message) {
        super(message);

        this.code = ResultStatusEnum.BUSINESS_ERROR;
    }

    public BusinessException(ResultStatusEnum code, String message) {
        super(message);

        this.code = code;
    }

    public BusinessException(ResultStatusEnum code, String message, Map<String, Object> data) {
        super(message);
        this.code = code;
        this.data = data;
    }

    public BusinessException(ResultStatusEnum code, Map<String, Object> data) {
        super(null != code ? code.getMsg() : null);
        this.code = code;
        this.data = data;
    }

    public BusinessException(ResultStatusEnum code) {

        super(null != code ? code.getMsg() : null);
        this.code = code;
    }

    public ResultStatusEnum getCode() {
        if (code == null) {
            return ResultStatusEnum.BUSINESS_ERROR;
        }
        return code;
    }

    public Object getData() {
        return data;
    }
}

package com.icbc.gjljfl.entity.exception;

import com.icbc.gjljfl.common.enums.ErrorEnum;

/**
 * @Author: 高一平
 * @Date: 2020/1/7
 * @Description:
 **/
public class ParamErrorException extends RuntimeException implements BaseException {

    private String code = ErrorEnum.PARAM_ERROR.getOutterCode();
    private String message = ErrorEnum.PARAM_ERROR.getOutterMsg();

    public ParamErrorException() {
    }

    public ParamErrorException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}

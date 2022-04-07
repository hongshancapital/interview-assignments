package com.jingdata.exception;

import lombok.Data;

/**
 * 自定义业务异常
 *
 * @Author
 * @Date
 */
@Data
public class BizException extends Exception {

    private Integer code;
    private String errorMessage;

    public BizException(ExceptionCode e) {
        this.code = e.getCode();
        this.errorMessage = e.getErrorMessage();
    }

}

package com.scdt.exception;

import com.scdt.domin.ErrorCode;
import lombok.Getter;

/**
 * BusinessException
 *
 * @author weixiao
 * @date 2022-04-26 11:54
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 3060366398871220855L;

    @Getter
    private final ErrorCode error;
    @Getter
    private final String msg;

    public BusinessException(ErrorCode error) {
        super(error.getCode() + ":" + error.getMsg());
        this.error = error;
        this.msg = error.getMsg();
    }

    public BusinessException(ErrorCode error, String msg) {
        super(error.getCode() + ":" + msg);
        this.error = error;
        this.msg = msg;
    }
}

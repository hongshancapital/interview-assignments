package com.example.shorturlservice.domain;

/**
 * @Description 业务异常
 * @Author xingxing.yu
 * @Date 2022/04/15 16:49
 **/
public class BizException extends RuntimeException {


    private static final long serialVersionUID = 1L;
    private Integer code;

    public BizException() {
    }


    public BizException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BizException(String message, Throwable cause, Integer code) {
        super(message, cause);
        this.code = code;
    }

    public BizException(Throwable cause, Integer code) {
        super((String) null, cause);
        this.code = code;
    }


    public Integer getCode() {
        return code;
    }
}

package com.diode.interview.domain.exception;

/**
 * @author unlikeha@163.com
 * @date 2022/4/28
 */
public class BizException extends RuntimeException {
    public BizException(String msg){
        super(msg);
    }

    public BizException(String msg, Exception e) {
        super(msg, e);
    }
}

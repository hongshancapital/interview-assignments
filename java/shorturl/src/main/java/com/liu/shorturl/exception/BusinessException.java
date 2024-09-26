package com.liu.shorturl.exception;

/**
 * Description： TODO
 * Author: liujiao
 * Date: Created in 2021/11/11 18:32
 * email: liujiao@fcbox.com
 * Version: 0.0.1
 */
public class BusinessException extends RuntimeException{
    /**
     * 错误码
     */
    protected Integer code;

    /**
     * 错误提示消息
     */
    protected String msg;

    public BusinessException(String msg) {
        super(msg);
    }
}

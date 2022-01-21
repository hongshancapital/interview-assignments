package com.layne.interview.model;


import lombok.Data;

/**
 * 异常
 *
 * @author layne
 * @version UrlManage.java, v 0.1 2022/1/17 23:21 下午
 */
@Data
public class ManageException extends RuntimeException {

    private static final long serialVersionUID = 6303198541197108135L;

    /**
     * 错误码
     */
    private ErrorCodeEnum errorCode;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 异常构造函数
     *
     * @param errorCode 错误码
     * @param errorMsg 错误信息
     */
    public ManageException(ErrorCodeEnum errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
    }
}

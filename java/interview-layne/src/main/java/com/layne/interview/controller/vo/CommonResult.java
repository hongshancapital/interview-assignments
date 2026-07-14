package com.layne.interview.controller.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * rest接口返回的通用结果
 *
 * @author layne
 * @version UrlManage.java, v 0.1 2022/1/17 22:39 下午
 */
@Data
public class CommonResult<T> implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -7619315797398644733L;

    /**
     * 结果数据
     */
    private T obj;

    /**
     * 请求处理结果
     */
    private boolean success = false;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 异常
     */
    private Exception exception;

}

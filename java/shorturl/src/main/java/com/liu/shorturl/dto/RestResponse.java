package com.liu.shorturl.dto;

import com.liu.shorturl.enums.ResultCodeEnum;

import java.io.Serializable;

/**
 * Description： 接口返回数据格式定义
 * Author: liujiao
 * Date: Created in 2021/11/11 18:23
 * email: liujiao@fcbox.com
 * Version: 0.0.1
 */
public class RestResponse <T> implements Serializable {

    /**
     * 是否响应成功
     */
    private Boolean success;
    /**
     * 响应状态码
     */
    private Integer code;
    /**
     * 响应数据
     */
    private T data;
    /**
     * 错误信息
     */
    private String message;

    /**
     * 有参构造器
     * @param obj
     */
    private RestResponse(T obj) {
        this.code = 200;
        this.data = obj;
        this.success = true;
    }

    /**
     * 有参构造器
     * @param resultCode
     */
    private RestResponse(ResultCodeEnum resultCode) {
        this.success = false;
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }



    /**
     * 返回成功（有返回结果）
     * @param data
     * @param <T>
     * @return
     */
    public static<T> RestResponse<T> success(T data){
        return new RestResponse<T>(data);
    }

    /**
     * 通用返回失败
     * @param resultCode
     * @param <T>
     * @return
     */
    public static<T> RestResponse<T> failure(ResultCodeEnum resultCode){
        return  new RestResponse<T>(resultCode);
    }


    public Boolean getSuccess() {
        return success;
    }


    public T getData() {
        return data;
    }



}

package com.meihua.shorturl.common.dto;

import com.meihua.shorturl.common.enums.ResponseCodeEnum;

import java.io.Serializable;
/**
 *  <p>
 *   response 基类
 *  </p>
 * @author meihua
 * @version 1.0
 * @date 2021/10/12
 */
public class BaseResponse<T> implements Serializable {

    public BaseResponse() {
    }

    private String message;

    private Integer code;

    private T data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    /**成功且带数据**/
    public static <T> BaseResponse<T> success( T v){
        BaseResponse res = new BaseResponse();
        res.setCode(ResponseCodeEnum.SUCCESS.getCode());
        res.setMessage(ResponseCodeEnum.SUCCESS.getMessage());
        res.setData(v);
        return res;
    }

    public static BaseResponse error(ResponseCodeEnum responseCodeEnum){
        BaseResponse result = new BaseResponse();
        result.setCode(responseCodeEnum.getCode());
        result.setMessage(responseCodeEnum.getMessage());
        return result;
    }
}
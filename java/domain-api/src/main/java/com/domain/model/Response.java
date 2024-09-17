package com.domain.model;


import lombok.Data;

/**
 * @author: xielongfei
 * @date: 2022/01/11
 * @description: 响应实体
 */
@Data
public class Response<T> {

    private int code;
    private String message;
    private T data;

    public static Response success(){
        return success(null);
    }


    public static Response success(Object obj){
        return success(obj, null);
    }

    public static Response success(Object obj, String msg){
        Response response = new Response();
        response.setCode(200);
        response.setMessage(msg);
        response.setData(obj);
        return response;
    }

    public static Response failed(int code, String msg){
        Response response = new Response();
        response.setCode(code);
        response.setMessage(msg);
        return response;
    }
}

package com.work.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class Result<T> {
    private boolean success;

    private Integer code;

    private String message;

    private T data;

    public  Result<T> success(T obj){
        Result<T> result = new Result<>();
        result.setData(obj);
        result.setCode(HttpStatus.OK.value());
        result.setSuccess(true);
        result.setMessage(HttpStatus.OK.getReasonPhrase());
        return result;
    }
    public  Result<T> success(T obj,String message){
        Result<T> result = this.success(obj);
        result.setMessage(message);
        return result;
    }
    public  Result<T> error(String message,Integer code){
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setSuccess(false);
        result.setMessage(message);
        return result;
    }
}

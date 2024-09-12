package com.oldnoop.shortlink.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ApiResult<T> {
    public static final int CODE_500 = 500;
    public static final int CODE_PARAM_ERROR = 400;
    public static final int CODE_SUCCESS = 0;

    private int code = CODE_SUCCESS;
    private String message;
    private boolean success;
    private T data;

    public static <T> ApiResult<T> success(){
        return new ApiResult<T>().setSuccess(true);
    }

    public static <T> ApiResult<T> success(T data){
        return new ApiResult<T>().setData(data).setSuccess(true);
    }

    public static <T> ApiResult<T> fail(int code, String message){
        return new ApiResult<T>().setCode(code).setMessage(message).setSuccess(false);
    }

    public static <T> ApiResult<T> fail(String message){
        return fail(CODE_500, message);
    }


}

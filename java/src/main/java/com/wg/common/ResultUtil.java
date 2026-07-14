package com.wg.common;

public class ResultUtil {

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result();
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        result.setData(data);
        return result;
    }

    public static <T> Result<T> error(Integer code, String message) {
        Result<T> result = new Result();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

}

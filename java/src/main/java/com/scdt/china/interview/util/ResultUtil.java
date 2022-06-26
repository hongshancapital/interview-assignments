package com.scdt.china.interview.util;


import com.scdt.china.interview.enm.ResultEnum;

/**
 * @author sujiale
 */
public class ResultUtil {
    /**
     * 正常返回结果
     */
    public static <T> Result<T> success(T data) {
        Result<T> objectResult = new Result<>();
        objectResult.setCode(ResultEnum.OK.getCode());
        objectResult.setMsg(ResultEnum.OK.getMessage());
        objectResult.setData(data);
        return objectResult;
    }

    /**
     *  异常返回结果
     */
    public static <T> Result<T> error(ResultEnum error) {
        Result<T> objectResult = new Result<>();
        objectResult.setCode(error.getCode());
        objectResult.setMsg(error.getMessage());
        return objectResult;
    }
}

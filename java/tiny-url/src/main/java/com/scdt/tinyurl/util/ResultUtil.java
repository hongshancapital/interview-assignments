package com.scdt.tinyurl.util;


import com.scdt.tinyurl.common.ErrorCode;
import com.scdt.tinyurl.common.ResponseResult;

public class ResultUtil {

    private ResultUtil() {
    }

    public static ResponseResult success(Object object, String msg) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(ErrorCode.SUCCESS.getCode());
        responseResult.setData(object);
        responseResult.setMsg(msg);
        return responseResult;
    }

    public static ResponseResult success(Object object) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(ErrorCode.SUCCESS.getCode());
        responseResult.setData(object);
        responseResult.setMsg("success");
        return responseResult;
    }

    public static ResponseResult success() {
        return success(null);
    }

    public static ResponseResult error(String code, String msg) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(code);
        responseResult.setMsg(msg);
        return responseResult;
    }

    public static ResponseResult error(String code, String msg,Object o) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(code);
        responseResult.setMsg(msg);
        responseResult.setData(o);
        return responseResult;
    }

}

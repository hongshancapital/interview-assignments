package com.sequoia.shortdomain.api;

import com.sequoia.shortdomain.common.ResponseResult;

public class BaseAPI {

    public ResponseResult success(String code, String msg, String data){
        ResponseResult result=new ResponseResult();
        result.setCode(code);
        result.setData(data);
        result.setMsg(msg);

        return result;
    }

    public ResponseResult failure(String code, String msg, String data){
        ResponseResult result=new ResponseResult();
        result.setCode(code);
        result.setData(data);
        result.setMsg(msg);

        return result;
    }
}

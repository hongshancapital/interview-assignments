package com.guigu.springcloud.Util;


import com.guigu.springcloud.entities.CommonResult;

import java.io.Serializable;

public class ResultUtil<T> implements Serializable {

    private CommonResult<T> result;

    public ResultUtil(){
      this.result = new CommonResult<>();
    }
    public  CommonResult<T> setData(T t){
        this.result.setCode(200);
        this.result.setMessage("SUCCESS");
        this.result.setData(t);
        return this.result;
    }
    public  CommonResult<T> setError(T t){
        this.result.setCode(404);
        this.result.setMessage("转换失败");
        this.result.setData(t);
        return this.result;
    }

}

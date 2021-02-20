package com.guigu.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {

    private Integer  code;
    private String message;
    private T data;
    public CommonResult(Integer  code,String message){
        this.code = code;
        this.message = message;
    }


}

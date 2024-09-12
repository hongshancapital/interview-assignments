package com.redwood.shorturl.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Description: 自定义异常类
 * @Author: Jack-ZG
 * @Date: 2021-06-08 18:04
 */
public class BizException extends RuntimeException{

    /**
     * 错误信息
     */
    @Getter
    private String msg;

    public BizException(String msg){
        this.msg = msg;
    };

}

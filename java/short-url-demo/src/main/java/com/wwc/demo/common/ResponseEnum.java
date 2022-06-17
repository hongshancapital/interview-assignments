package com.wwc.demo.common;

/**
 * @description：返回状态码和说明集合
 * @date：2022年04月27日 20时08分29秒
 * @author：汪伟成
**/
public enum ResponseEnum {
    SUCCESS(200,"请求成功！"),
    NOT_EXISTS(404,"该短码长链接不存在！"),
    EXCEPT(500,"系统内部异常");
    private Integer code;
    private String msg;
    ResponseEnum(Integer code,String msg){
        this.code=code;
        this.msg=msg;
    }
    public Integer getCode(){
        return code;
    }
    public String getMsg(){
        return msg;
    }
}

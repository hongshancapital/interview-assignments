package com.wwc.demo.common;

/**
 * @description：通用接口返回类
 * @date：2022年04月27日 20时08分46秒
 * @author：汪伟成
**/
public class ResponseResult {
    private Integer code;
    private String msg;
    private Object data;
    public ResponseResult(Integer code,String msg,Object data){
        this.code=code;
        this.msg=msg;
        this.data=data;
    }

    public ResponseResult(ResponseEnum responseEnum,Object data){
        this.code= responseEnum.getCode();
        this.msg=responseEnum.getMsg();
        this.data=data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

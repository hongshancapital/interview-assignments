package com.wangxingchao.shorturl.utils;

import com.wangxingchao.shorturl.utils.enums.ResultErrorEnum;

/**
 * 前端信息返回类
 */
// 可以使用lombok来简化代码，但要看公司考不考虑lombok驼峰导致的json异常问题
public class Result {

    // 返回编码 默认 200 为成功
    private int code = 200;

    // 返回数据
    private Object data;

    // 错误信息
    private String errorMsg;

    public Result(Object data) {
        this.data = data;
    }

    public Result(ResultErrorEnum errorEnum) {
        this.code = errorEnum.code;
        this.errorMsg = errorEnum.msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", data=" + data +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}

package com.interview.common;

import lombok.Data;

/**
 * @Description :
 * @Author: nyacc
 * @Date: 2021/12/17 14:08
 */

public enum UrlTranslationError {

    /**
     * url非法
     */
    URL_INVALID("100","URL非法"),

    /**
     * 计数器达到上限
     */
    COUNTER_FULL("200","生成短链已达上限,无法继续生成短链,请联系管理员");

    private String code;

    private String msg;

    UrlTranslationError(String code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

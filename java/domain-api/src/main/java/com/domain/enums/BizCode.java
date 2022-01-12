package com.domain.enums;

/**
 * @author: xielongfei
 * @date: 2022/01/11
 * @description: 业务code枚举
 */

public enum BizCode {

    OK(200, "成功"),
    PARAM_NOT_NULL(10001, "参数不能为空"),
    NO_DATA_MATCHED(10002, "未匹配到数据");

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private BizCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public boolean test(int code) {
        return this.code == code;
    }


}

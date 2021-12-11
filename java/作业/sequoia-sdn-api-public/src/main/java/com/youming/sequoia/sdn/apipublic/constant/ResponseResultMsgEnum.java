package com.youming.sequoia.sdn.apipublic.constant;

public enum ResponseResultMsgEnum {

    BAD_REQUEST_PARAM(-2, "提交参数错误"), // 请求的参数，或格式不对，或长度不对
    GO_MARS(-1, "系统繁忙请稍后再试"), // 异常型错误
    SUCCESS(0, "调用成功"), // 调用接口成功
    NO_LOGIN(1, "你没有登录"), // 未登录就调用需要权限的接口
    NO_PERMISSION(2, "权限不够禁止访问"), // 没有权限使用接口
    

    ;

    private final int code;
    private final String msg;

    private ResponseResultMsgEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


}

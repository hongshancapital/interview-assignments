package com.url.error;

/**
 * 业务编码配置
 * @Author jeckzeng
 * @Date 2022/4/30
 * @Version 1.0
 */
public enum BusinessCode {

    SUCCESS(200,"success!"),
    PARAMETER_EMPTY(400,"域名参数格式错误，请输入正确的域名!"),
    SERVER_ERROR(500,"服务异常，请稍后重试!"),
    EXPIRE_OR_NOT_EXISTS(600,"长域名不存在或已过期!"),
    ;

    public Integer code;

    public String msg;

    public Integer code() {
        return code;
    }

    public String msg() {
        return msg;
    }

    BusinessCode(Integer code, String errorMsg) {
        this.code = code;
        this.msg = errorMsg;
    }
}

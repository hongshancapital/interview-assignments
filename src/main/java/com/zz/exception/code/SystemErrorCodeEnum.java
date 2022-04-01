package com.zz.exception.code;

/**
 * 系统错误编码
 *
 * @author zz
 * @version 1.0
 * @date 2022/3/31
 */
public enum SystemErrorCodeEnum implements ErrorCode {
    SYS_001("请求系统超时"),
    SYS_002("限流异常"),
    SYS_999("系统异常");

    private String desc;

    SystemErrorCodeEnum(String desc) {
        this.desc = desc;
    }

    @Override
    public java.lang.String getCode() {
        return name();
    }

    @Override
    public String getMessage() {
        return this.desc;
    }
}

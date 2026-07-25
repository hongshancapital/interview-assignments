package com.liu.shorturl.enums;

/**
 * Description： 请求响应状态码枚举类
 * Author: liujiao
 * Date: Created in 2021/11/11 18:35
 * email: liujiao@fcbox.com
 * Version: 0.0.1
 */
public enum ResultCodeEnum {

    // 请求成功
    SUCCESS(200, "请求成功"),

    // 服务器错误
    SERVER_ERROR(500,"服务器错误"),

    //自定义 域名不合法
    URL_INVALID(5000, "域名不合法");

    /**
     * 响应状态码
     */
    private Integer code;
    /**
     * 响应信息
     */
    private String message;

    ResultCodeEnum(Integer code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

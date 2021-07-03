package com.shorturl.common;

import java.io.Serializable;

/**
 * 响应结果状态码
 *
 * @author noodle
 */
public class CodeMsg implements Serializable {

    /**
     * 通用异常
     */
    public static CodeMsg SUCCESS = new CodeMsg(200, "success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
    public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常");
    public static CodeMsg REQUEST_ILLEGAL = new CodeMsg(500102, "请求非法");
    public static CodeMsg ACCESS_LIMIT_REACHED = new CodeMsg(500104, "访问太频繁！");
    private int code;
    private String msg;

    /**
     * 构造器定义为private是为了防止controller直接new
     *
     * @param code
     * @param msg
     */
    public CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 动态地填充msg字段
     *
     * @param args
     * @return
     */
    public CodeMsg fillArgs(Object... args) {
        int code = this.code;
        String message = String.format(this.msg, args);// 将arg格式化到msg中，组合成一个message
        return new CodeMsg(code, message);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}


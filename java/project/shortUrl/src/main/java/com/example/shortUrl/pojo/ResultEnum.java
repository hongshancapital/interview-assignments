package com.example.shortUrl.pojo;



/**
 * @Author HOPE
 * @Description 状态码枚举类
 * @Date 2022/5/1 20:25
 */
public enum ResultEnum {
    SUCCESS(200,"请求成功"),
    NOT_EXISTS(404,"短链接不存在"),
    ERROR(500,"系统内部异常");
    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
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
}


package com.shortlink.common;

/**\
 * 服务状态码
 */
public enum Status {


    INVALID_PARAM(1001, "参数不合法"),
    TRY_AGAIN(1002, "服务请重试"),
    INVALID_RESULT(1003, "结果不合法"),
    SERVER_ERROR(1004, "服务异常"),
    DATA_CENTER_ID_ERROR(1005, "机房ID超限"),
    MACHINE_ID_ERROR(1006, "机器ID超限"),
    SUCCESS(0, "成功");

    private Integer code;
    private String desc;

    Status (Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}

package com.snail.shorturlservice.common.response;

public enum ResultCode {
    SUCCESS(1, "操作成功！"),
    NOT_FOUND(4040, "接口不存在"),

    PARAM_ERROR(5010, "参数错误"),
    PARAM_FORMAT_ERROR(5020, "参数格式错误"),
    DUPLICATE_KEY(5030, "数据库主键冲突"),
    DATA_INSERT_ERROR(5031, "数据库入库异常"),
    UNKNOWN_ERROR( 5999, "未知错误");

    int code;
    String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

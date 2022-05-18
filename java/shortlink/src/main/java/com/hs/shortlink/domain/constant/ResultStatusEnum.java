package com.hs.shortlink.domain.constant;

public enum ResultStatusEnum {
    /**
     * 通用前端状态返回错误码和信息
     */
    SUCCESSES("请求成功", 0),

    PARAM_INVALID("参数不合法", 1001),
    BUSINESS_ERROR("业务错误", 1002),
    NUMBER_FORMAT_ERROR("数字格式错误", 1003),
    LINK_NOT_EXISTS("链接不存在", 1004),

    ;

    private String msg;
    private int error;

    ResultStatusEnum(String msg, int error) {
        this.msg = msg;
        this.error = error;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

}

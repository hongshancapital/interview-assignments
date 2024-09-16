package com.yuanyang.hongshan.entity;

/**
 * @author yuanyang
 * @date 2021/12/16 5:20 下午
 * @Describe
 */

public enum ResultCode {
    /**
     * 成功.
     */
    SUCCESS("0000", "成功"),
    /**
     * 参数不合法.
     */
    ILLEGAL_PARAM("0001","参数不合法"),
    /**
     * Service内部错误.
     */
    SERVICE_INTERNAL_ERROR("0002","Service内部错误"),
    /**
     * 数据异常
     */
    ILLEGAL_DATA("0003","数据不存在或异常，请检查"),
    /**
     * 短链数据过期
     */
    ILLEGAL_DATA_NO_VALID("0004","短链接失效")
    ;

    private String code;
    private String msg;

    private ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "ResultCode{" +
            "code='" + code + '\'' +
            ", msg='" + msg + '\'' +
            "} " + super.toString();
    }

}

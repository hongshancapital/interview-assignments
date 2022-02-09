package com.example.scdt.constant;

/**
 * @author JonathanCheung
 * @date 2021/12/11 17:53
 * @describe
 */
public enum CommonRespCode {

    REQ_SUCCESS("000000", "SUCCESS", ""),
    READ_FAIL("100000", "读取长域名信息失败", ""),
    UNKONWN_ERROR("999999", "UNKONWN_ERROR", "");

    /**
     * 响应码编码
     */
    private String code;
    /**
     * 中文通用描述
     */
    private String msg;
    /**
     * 预留字段，暂时不用
     */
    private String detail;

    CommonRespCode(String code, String msg, String detail) {
        this.code = code;
        this.msg = msg;
        this.detail = detail;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}

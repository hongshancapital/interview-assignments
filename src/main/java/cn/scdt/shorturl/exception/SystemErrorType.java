package cn.scdt.shorturl.exception;

import lombok.Getter;

@Getter
public enum SystemErrorType implements ErrorType {

    SYSTEM_SUCCESS("0000", "操作成功"),
    SYSTEM_ERROR("-1", "系统异常"),
    SYSTEM_BUSY("000001", "系统繁忙,请稍候再试"),
    NOT_EXISTS_FULLURL("120001","未能找到匹配的长域名"),
    IRREGULAR_URL("120002","不合规的URL"),
    DUPLICATE_PRIMARY_KEY("030000","唯一键冲突");

    /**
     * 错误类型码
     */
    private String code;
    /**
     * 错误类型描述信息
     */
    private String mesg;

    SystemErrorType(String code, String mesg) {
        this.code = code;
        this.mesg = mesg;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMesg() {
        return mesg;
    }
}

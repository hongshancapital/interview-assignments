package com.icbc.gjljfl.common.enums;

public enum ErrorEnum {
    //基础业务码
    SUCCESS("0", "success", "0", "成功"),
    FAIL("-1", "exception", "-1", "失败"),
    FAILException("500", "Exception", "500", "程序异常"),

    BUSINESS_SUCCESS("0", "该业务办理成功", "0", "业务办理成功"),
    BUSINESS_FAIL("-1", "该业务办理失败", "-1", "业务办理失败"),

    ERROR("999", "系统异常,请稍后重试", "E_9999", "系统内部异常,请稍后重试"),

    HTTP_METHOD_FAIL("110", "请求方式错误", "110", "请求方式错误"),

    PARAM_ERROR("400", "参数错误", "400", "参数错误"),
    PARAM_READABLE_ERROR("401", "参数不可解析", "401", "参数不可解析"),

    SQL_ERROR("501", "数据库操作有误", "501", "数据库操作有误"),
    //二维码模块  error
    QW_1("-1", "无此用户","-1", "无此用户"),
    QW_2("-1","参数不完整","-1","参数不完整"),
    QW_3("-1","修改类型失败","-1","操作类型错误"),
    QW_4("-1","无此二维码信息","-1","无此二维码信息"),
    QW_5("-1","你好！达到评分上限","-1","你好！评分达到上限"),

    ;
    private String innerCode; //对内响应码
    private String innerMsg;  //对内响应信息
    private String outterCode;  //对外响应码
    private String outterMsg;   //对外响应信息

    public String getInnerCode() {
        return innerCode;
    }

    public void setInnerCode(String innerCode) {
        this.innerCode = innerCode;
    }

    public String getInnerMsg() {
        return innerMsg;
    }

    public void setInnerMsg(String innerMsg) {
        this.innerMsg = innerMsg;
    }

    public String getOutterCode() {
        return outterCode;
    }

    public void setOutterCode(String outterCode) {
        this.outterCode = outterCode;
    }

    public String getOutterMsg() {
        return outterMsg;
    }

    public void setOutterMsg(String outterMsg) {
        this.outterMsg = outterMsg;
    }

    ErrorEnum(String innerCode, String innerMsg, String outterCode, String outterMsg) {
        this.innerCode = innerCode;
        this.innerMsg = innerMsg;
        this.outterCode = outterCode;
        this.outterMsg = outterMsg;
    }
}

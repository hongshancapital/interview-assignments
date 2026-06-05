package com.zy.url.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("返回信息包装类")
public class ResultVo<T> {
    public static final int OK_CODE = 0;
    private static final String DEFAULT_MESSAGE = "OK";
    private static final ResultVo<Void> OK_VO;

    static {
        OK_VO = new ResultVo<>(OK_CODE, DEFAULT_MESSAGE);
    }

    public ResultVo() {
    }

    public ResultVo(int code, String errorMsg) {
        this(code + "", errorMsg);
    }

    public ResultVo(String code, String errorMsg) {
        this(code, errorMsg, null);
    }

    public ResultVo(int code, String errorMsg, T content) {
        this(code + "", errorMsg, content);
    }

    public ResultVo(String code, String errorMsg, T content) {
        this.code = code;
        this.errorMsg = errorMsg;
        this.content = content;
    }

    public static <T> ResultVo<T> ok(T content) {
        return new ResultVo<>(OK_CODE, null, content);
    }

    public static ResultVo<Void> ok() {
        return OK_VO;
    }


    @ApiModelProperty(
            value = "返回码",
            example = "0",
            notes = "0:正常,-1:参数异常,-2系统异常,其它:相关业务异常")
    private String code;

    @ApiModelProperty(
            value = "错误消息",
            notes = "只有 code!=0 时才有值")
    private String errorMsg;

    @ApiModelProperty("业务数据")
    private T content;

    public String getCode() {
        return code;
    }

    public void setCode(String returnCode) {
        this.code = returnCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}

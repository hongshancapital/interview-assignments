package com.example.bean.result;

import com.example.bean.mdc.MdcContext;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ResultRpc<T> implements Serializable {

    private static final long serialVersionUID = -637206739894470702L;

    public static final String SUCCESS = "0";
    public static final String SUCESS_MESSAGE = "调用成功";
    public static final String EXCEPTION = "-100";
    public static final String EXCEPTION_MESSAGE = "系统开小差了，请稍后再试";
    public static final Integer ILLEGAL_ARGUMENT_EXCEPTION = 1;
    public static final Integer BUSINESS_EXCEPTION = 2;

    private String code;

    private String msg;

    private Integer msgType;

    private T data;

    private String TrandsId;

    public ResultRpc() {
        super();
        this.TrandsId = MdcContext.getTraceId();
    }

    public ResultRpc(String code, String msg, T data) {
        super();
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.TrandsId = MdcContext.getTraceId();
    }

    public ResultRpc(String code, String msg, Integer msgType, T data) {
        super();
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.msgType = msgType;
        this.TrandsId = MdcContext.getTraceId();
    }

    public ResultRpc(String code, String msg, Integer msgType, T data, String tid) {
        super();
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.msgType = msgType;
    }

    public boolean isSuccess() {
        return (SUCCESS.equals(this.code));
    }

    public boolean isFail() {
        return (SUCCESS.equals(this.code));
    }

    public static <T> ResultRpc<T> getSuccessResult(T data){
        return new ResultRpc<>(SUCCESS, SUCESS_MESSAGE, data);
    }

    public static <T> ResultRpc<T> getSuccessWithoutResult(){
        return new ResultRpc<>(SUCCESS, SUCESS_MESSAGE, null);
    }

    public static <T> ResultRpc<T> getExceptionResult() {
        return new ResultRpc<>(EXCEPTION, EXCEPTION_MESSAGE, null);
    }

    public static <T> ResultRpc<T> getErrorResult(String code, String errorMsg) {
        return new ResultRpc<>(code, String.format("%s", errorMsg), null);
    }

    public static <T> ResultRpc<T> getErrorResult(ResultRpcEnum resultRpcEnum) {
        return new ResultRpc<>(resultRpcEnum.getCode(), resultRpcEnum.getDesc(), null);
    }

    public static <T> ResultRpc<T> getErrorResult(String code, String errorMsg, T data) {
        return new ResultRpc<>(code, errorMsg, data);
    }

    public static <T> ResultRpc<T> getErrorResultWithMsg(String code, String errorMsg) {
        return new ResultRpc<>(code, String.format("%s", errorMsg), null);
    }

    public static <T> ResultRpc<T> fail(){
        return new ResultRpc<>(EXCEPTION, EXCEPTION_MESSAGE, null);
    }

    public static <T> ResultRpc<T> success(T obj){
        return new ResultRpc<T>(SUCCESS, SUCESS_MESSAGE, obj);
    }

    public static <T> ResultRpc<T> failWithMsg(String msg){
        return new ResultRpc<>(EXCEPTION, msg, null);
    }

}
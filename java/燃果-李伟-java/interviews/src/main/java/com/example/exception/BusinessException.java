package com.example.exception;

import com.example.bean.mdc.MdcContext;
import com.example.bean.result.ResultRpcEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * 业务异常
 * - Service抛出，Controller捕获
 * @author eric
 * @date 2021/7/21 22:21
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class BusinessException extends RuntimeException {
    // 异常id
    private String eid;

    // 错误码
    private String code;

    // 错误消息
    private String message;

    // 错误数据
    private Object edata;

    private BusinessException(String exceptionId, String code, String message, Object errData, Throwable e){
        super(e);
        this.eid = exceptionId;
        this.code = code;
        this.message = message;
        this.edata = errData;
    }

    public BusinessException(String message){
        this(MdcContext.getTraceId(), ResultRpcEnum.FAILED.getCode(), message, null, null);
    }

    public BusinessException(String message, Throwable e){
        this(MdcContext.getTraceId(), ResultRpcEnum.FAILED.getCode(), message, null, e);
    }

    public BusinessException(String message, Object errData){
        this(MdcContext.getTraceId(), ResultRpcEnum.FAILED.getCode(), message, errData, null);
    }

    public BusinessException(ResultRpcEnum status){
        this(MdcContext.getTraceId(), status.getCode(), status.getDesc(), null, null);
    }

    public BusinessException(ResultRpcEnum status, Object errData){
        this(MdcContext.getTraceId(), status.getCode(), status.getDesc(), errData, null);
    }

    public BusinessException(ResultRpcEnum status, Throwable e){
        this(MdcContext.getTraceId(), status.getCode(), status.getDesc(), null, e);
    }

    public void print(){
        log.error(this.getEid() + ": " + this.getCode() + "-" + this.getMessage(), this);
    }
}

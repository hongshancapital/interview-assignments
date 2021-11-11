package com.lynnhom.sctdurlshortservice.common.exception;


import com.lynnhom.sctdurlshortservice.common.enums.RespCodeEnum;

/**
 * 业务异常
 *
 * @author: lynnhom
 * @date: 3:27 下午 2021/7/18
 */
public class BizException extends RuntimeException {
    private String bizCode = "-1";
    private String bizMsg;

    public BizException() {
        super();
    }

    public BizException(String bizMsg) {
        super(bizMsg);
        this.bizMsg = bizMsg;
    }

    public BizException(Throwable cause) {
        super(cause);
    }

    public BizException(String bizCode, String bizMsg) {
        super(bizMsg);
        this.bizCode = bizCode;
        this.bizMsg = bizMsg;
    }

    public BizException(String bizMsg, Throwable cause) {
        super(bizMsg, cause);
        this.bizMsg = bizMsg;
    }

    public BizException(RespCodeEnum respCodeEnum) {
        super(respCodeEnum.getMessage());
        this.bizCode = respCodeEnum.getCode();
        this.bizMsg = respCodeEnum.getMessage();
    }

    public String getBizCode() {
        return bizCode;
    }

    public String getBizMsg() {
        return bizMsg;
    }
}

package com.wwwang.assignment.shortenurl.exception;

import lombok.Getter;

@Getter
public class BizException extends RuntimeException{

    private String bizMsg;

    public BizException(String bizMsg){
        this.bizMsg = bizMsg;
    }

    public BizException(String bizMsg, Throwable cause){
        super(cause);
        this.bizMsg = bizMsg;
    }

}

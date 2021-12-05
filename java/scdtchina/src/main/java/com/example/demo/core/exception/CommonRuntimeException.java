package com.example.demo.core.exception;

public class CommonRuntimeException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 5138163717239184804L;

    public CommonRuntimeException(String msg){
        super(msg);
    }
    public CommonRuntimeException(){
        super();
    }
}
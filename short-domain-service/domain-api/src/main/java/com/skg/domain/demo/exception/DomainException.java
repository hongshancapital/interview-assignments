package com.skg.domain.demo.exception;

/**
 * @Author smith skg
 * @Date 2021/10/13 17:45
 * @Version 1.0
 */
public class DomainException extends RuntimeException{

    public DomainException(String message){
        super(message);
    }
}

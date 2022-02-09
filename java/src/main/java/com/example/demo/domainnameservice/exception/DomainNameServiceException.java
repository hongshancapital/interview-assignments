package com.example.demo.domainnameservice.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * url encoding exception.
 *
 * @author laurent
 * @date 2021-12-11 下午3:51
 */
@Setter
@Getter
public class DomainNameServiceException extends RuntimeException{

    private final Integer code;

    public DomainNameServiceException(final Integer errorCode, final String msg) {
        super(msg);
        this.code = errorCode;
    }
}

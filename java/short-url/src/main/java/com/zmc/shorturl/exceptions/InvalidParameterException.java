package com.zmc.shorturl.exceptions;

/**
 * Description: 参数错误异常
 * Author: Zmc
 * Date: 2021-12-11 18:20
 **/
public class InvalidParameterException extends RuntimeException {

    public InvalidParameterException(String message) {
        super(message);
    }
}

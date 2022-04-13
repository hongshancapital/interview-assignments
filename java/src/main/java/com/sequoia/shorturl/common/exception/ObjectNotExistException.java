package com.sequoia.shorturl.common.exception;

/**
 * @Author: yanggj
 * @Description: 对象不存在异常
 * @Date: 2022/1/4 22:59
 * @Version: 1.0.0
 */
public class ObjectNotExistException extends RuntimeException{
    public ObjectNotExistException(String message) {
        super(message);
    }
}

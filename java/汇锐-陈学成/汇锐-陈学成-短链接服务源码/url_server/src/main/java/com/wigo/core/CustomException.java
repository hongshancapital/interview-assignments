package com.wigo.core;

/**
 * @author wigo.chen
 * @date 2021/7/27 9:52 下午
 * Introduction: 自定义异常类
 */
public class CustomException extends Exception {
    public CustomException() {
    }

    public CustomException(String message) {
        super(message);
    }
}

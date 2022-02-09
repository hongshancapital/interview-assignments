package com.example.scdt.exception;

/**
 * @author JonathanCheung
 * @date 2021/12/11 17:46
 * @describe
 */
public class BusinessException extends Exception{

    // 异常信息
    private final String message;

    // 异常错误码
    private final String code;


    public BusinessException(String message, String code) {
        this.message = message;
        this.code = code;
    }


    @Override
    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}

package com.zhangzheng.homework.exception;

/**
 * @author zhangzheng
 * @version 1.0
 * @description: TODO
 * @date 2021/10/9 下午3:23
 */
public enum SystemType implements ErrorType {
    SUCCESS(200, "成功"),
    ERROR(100, "服务异常，请稍后再试"),
    ERROR_100000(100000, "入参不能为空");

    private int result;
    private String message;

    private SystemType(int result, String message) {
        this.result = result;
        this.message = message;
    }

    public int getResult() {
        return this.result;
    }

    public String getMessage() {
        return this.message;
    }
}


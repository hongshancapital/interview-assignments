package com.bolord.shorturl.exception;

/**
 * 短链接自定义异常
 */
public class ShortUrlException extends RuntimeException {

    /**
     * HTTP 状态码
     */
    private int code;

    /**
     * 错误消息
     */
    private String message;

    /**
     * 构造函数
     * @param code HTTP 状态码
     * @param message 错误消息
     */
    public ShortUrlException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

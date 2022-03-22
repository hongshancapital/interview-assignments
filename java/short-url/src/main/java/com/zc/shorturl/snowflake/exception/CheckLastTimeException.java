package com.zc.shorturl.snowflake.exception;

/**
 * 检测snowflake上次更新时间异常
 */
public class CheckLastTimeException extends RuntimeException {
    public CheckLastTimeException(String msg) {
        super(msg);
    }
}

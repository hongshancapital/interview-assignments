package com.scdt.shortlink.exception;

/**
 * 生成短链异常
 * @Author tzf
 * @Date 2022/5/1
 */
public class CreateShortLinkException extends RuntimeException{
    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public CreateShortLinkException(String message) {
        super(message);
    }
}

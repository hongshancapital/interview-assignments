package com.example.app.common.exception;

/**
 * 模块异常
 *
 * @author voidm
 * @date 2021/9/18
 */
public class ModuleException extends Exception {

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public ModuleException(String message) {
        super(message);
    }
}
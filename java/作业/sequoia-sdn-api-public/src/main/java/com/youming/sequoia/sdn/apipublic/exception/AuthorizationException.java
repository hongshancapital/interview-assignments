package com.youming.sequoia.sdn.apipublic.exception;

/**
 * 权限不够时的异常
 */
public class AuthorizationException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -9180485710715197478L;

    public AuthorizationException() {
        super();
    }


    public AuthorizationException(String message) {
        super(message);
    }

    public AuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }


}

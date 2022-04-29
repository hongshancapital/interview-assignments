package com.youming.sequoia.sdn.apipublic.exception;

public class UnauthenticatedException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -9180485710715197478L;

    public UnauthenticatedException() {
        super();
    }


    public UnauthenticatedException(String message) {
        super(message);
    }

    public UnauthenticatedException(String message, Throwable cause) {
        super(message, cause);
    }


}

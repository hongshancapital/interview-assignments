package com.jerry.domainservice.api.exception;

public class ServiceRejectException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 494228372320970700L;

	public ServiceRejectException(String message) {
        super(message);
    }
	public ServiceRejectException(String message,Throwable throwable) {
        super(message,throwable);
    }
}

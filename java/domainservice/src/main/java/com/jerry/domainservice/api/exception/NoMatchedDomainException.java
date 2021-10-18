package com.jerry.domainservice.api.exception;

public class NoMatchedDomainException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2926083377203992362L;
	public NoMatchedDomainException(String message) {
        super(message);
    }
}

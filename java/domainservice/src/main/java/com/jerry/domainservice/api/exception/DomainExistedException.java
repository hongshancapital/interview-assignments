package com.jerry.domainservice.api.exception;

public class DomainExistedException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8773542687918591976L;

	public DomainExistedException(String message) {
        super(message);
    }
}

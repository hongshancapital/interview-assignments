package com.jerry.domainservice.api.cache.exception;

public class CachedObjectAlreadyExistedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2152882495338101864L;

	public CachedObjectAlreadyExistedException(String message) {
        super(message);
    }
}

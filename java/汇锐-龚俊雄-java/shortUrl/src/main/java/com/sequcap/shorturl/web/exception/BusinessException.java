package com.sequcap.shorturl.web.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -8528672763604806248L;

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}
}

package com.sequoia.urllink.base.exception;

/**
 * 业务验证异常
 * 
 */
public class InvalidBusinessException extends RuntimeException {

	private static final long serialVersionUID = -6726526519104530748L;

	public InvalidBusinessException() {
		super();
	}

	public InvalidBusinessException(String message) {
		super(message);
	}

	public InvalidBusinessException(Throwable cause) {
		super(cause);
	}

	public InvalidBusinessException(String message, Throwable cause) {
		super(message, cause);
	}

}

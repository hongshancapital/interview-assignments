package com.sequoia.urllink.base.exception;

/**
 * 非法参数异常
 * 
 */
public class InvalidParamException extends RuntimeException {

	private static final long serialVersionUID = -6726526519104530748L;

	public InvalidParamException() {
		super();
	}

	public InvalidParamException(String message) {
		super(message);
	}

	public InvalidParamException(Throwable cause) {
		super(cause);
	}

	public InvalidParamException(String message, Throwable cause) {
		super(message, cause);
	}

}

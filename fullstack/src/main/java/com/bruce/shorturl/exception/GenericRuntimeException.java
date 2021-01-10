package com.bruce.shorturl.exception;

import lombok.Data;

/**
 * 通用runtime异常
 * @author bruce
 */
@Data
public class GenericRuntimeException extends RuntimeException{

	/** 错误码 */
	private Integer errCode;
	/** 错误消息 */
	private String errMsg;

	public GenericRuntimeException() {
	}

	public GenericRuntimeException(Integer errCode) {
		this.errCode = errCode;
	}

	public GenericRuntimeException(Integer errCode, String errMsg) {
		this.errCode = errCode;
		this.errMsg = errMsg;
	}


}

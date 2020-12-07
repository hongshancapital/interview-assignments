package com.bruce.shorturl.exception;

import lombok.Data;

/**
 * 通用异常
 * @author bruce
 */
@Data
public class GenericException extends Exception{


	/** 错误码 */
	private Integer errCode;
	/** 错误消息 */
	private String errMsg;

	public GenericException() {
	}

	public GenericException(Integer errCode) {
		this.errCode = errCode;
	}

	public GenericException(Integer errCode, String errMsg) {
		this.errCode = errCode;
		this.errMsg = errMsg;
	}


}

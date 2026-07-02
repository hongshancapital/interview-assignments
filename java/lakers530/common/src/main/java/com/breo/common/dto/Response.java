package com.breo.common.dto;

import lombok.Data;

@Data
public class Response {
	
	/** 1：正常 0：异常 */
	private int result = 1;
	
	/** 错误码 */
	private String code = "";
	
	/** 错误信息 */
	private String msg = "";
	
	
	public void setErrorResponse(String code, String msg) {
		this.code  = code;
		this.msg  = msg;
		this.result = 0;
	}
	
}

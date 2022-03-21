package com.scdtchina.common;

/**
 * @author hai.yuan
 * 系统枚举值
 */

public enum ResultCode {

	PARAM_IS_INVALID(1001, "无效链接"),
	SYSTEM_CAPACITY_FULL(1002, "系统容量已达上限");

	public Integer code;
	public String message;

	ResultCode(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

}

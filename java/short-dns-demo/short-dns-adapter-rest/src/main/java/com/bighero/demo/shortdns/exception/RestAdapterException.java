 package com.bighero.demo.shortdns.exception;

import com.bighero.demo.shortdns.domain.exception.AdapterException;

/**
 * restful异常类
 * @author bighero
 */
public class RestAdapterException extends AdapterException {

	/**
	 * @param code
	 * @param msg
	 */
	public RestAdapterException(String code, String msg) {
		super(code, msg);
	}

}

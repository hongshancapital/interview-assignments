/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.scdt.urlshorter.dto;

import com.scdt.urlshorter.constant.HttpStatus;
import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 *
 * @author Mark sunlightcs@gmail.com
 */
public class ResultResponse extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	
	public ResultResponse() {
		put("code", 0);
		put("msg", "success");
	}
	
	public static ResultResponse error() {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
	}
	
	public static ResultResponse error(String msg) {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
	}
	
	public static ResultResponse error(int code, String msg) {
		ResultResponse r = new ResultResponse();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static ResultResponse success(String msg) {
		ResultResponse r = new ResultResponse();
		r.put("msg", msg);
		return r;
	}
	
	public static ResultResponse success(Map<String, Object> map) {
		ResultResponse r = new ResultResponse();
		r.putAll(map);
		return r;
	}
	
	public static ResultResponse success() {
		return new ResultResponse();
	}

	@Override
	public ResultResponse put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}

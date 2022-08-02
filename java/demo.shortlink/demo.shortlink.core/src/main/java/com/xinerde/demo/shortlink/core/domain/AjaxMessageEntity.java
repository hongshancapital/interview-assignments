package com.xinerde.demo.shortlink.core.domain;

/**
 * 封装json请求返回的标准json对象
 * 
 * @since 2022年5月19日
 * @author guihong.zhang
 * @version 1.0
 */
public class AjaxMessageEntity<T> {

	private int code = 0;

	private String msg = "OK";

	T data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public void setMessager(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

}

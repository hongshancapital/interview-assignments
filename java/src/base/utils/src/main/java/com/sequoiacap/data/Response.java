package com.sequoiacap.data;


import java.io.Serializable;
import java.util.HashMap;

public class Response<T>
	implements Serializable
{
	private static final long serialVersionUID = 2318866198328783813L;

	/**
	 * 状态值
	 */
	private Integer status = 200;

	/**
	 * 错误信息
	 */
	private String message;

	/**
	 * 数据
	 */
	private T result;

	public T getResult()
	{
		return result;
	}

	public void setResult(T result)
	{
		this.result = result;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

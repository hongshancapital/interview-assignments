package com.interview.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;

/**
 * 
 * @类名称   RestResponse.java
 * @类描述   <pre>API接口响应类</pre>
 * @作者     zhangsheng
 * @创建时间 2021年3月21日下午3:52:10
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本           修改人 		    修改日期 		           修改内容描述
 *     ------------------------------------------------------
 *     1.00 	zhangsheng 	2021年3月21日下午3:52:10             
 *     ------------------------------------------------------
 * </pre>
 */
@ApiModel(value = "RestResponse", description = "响应对象")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResponse<T> {
	
	/**
	 * 默认成功业务响应码.
	 */
	public static final int DEFAULT_SUCCESS_RESPONSE_CODE = 0;
	
	/**
	 * 默认失败业务响应码.
	 */
	public static final int DEFAULT_FAILED_RESPONSE_CODE = -1;
	

	/**
	 * 默认成功响应提示消息.
	 */
	public static final String DEFAULT_SUCCESS_RESPONSE_MESSAGE = "ok";
	
	/**
	 * 默认失败响应提示消息.
	 */
	public static final String DEFAULT_FAILED_RESPONSE_MESSAGE = "error";
	
	/*
	 * 业务响应码 ，成功处理的请求的业务响应码默认是0,某些情况下，因输入不符合要求等原因导致请求是成功的但业务是失败的，此时置为非0。
	 */
	private int code = DEFAULT_SUCCESS_RESPONSE_CODE; 
	
	/*
	 * 正常响应时，存放结果
	 */
	private T data; 
	
	/*
	 * 正常响应时，"OK"; 异常响应时，存放错误提示.
	 */
	private String message = DEFAULT_SUCCESS_RESPONSE_MESSAGE; 
	
	private RestResponse() {
		super();
	}

	private RestResponse(T data) {
		super();
		this.data = data;
	}

	/**
	 * 空响应，没有data,仅有表示成功的业务响应码和message。
	 * @return
	 */
	public static <T> RestResponse<T> empty(){
		return new RestResponse<T>();
	}
	
	/**
	 * 成功响应，包含返回结果.
	 * @param data
	 * @return
	 */
	public static <T> RestResponse<T> ok(T data){
		return new RestResponse<>(data);
	}
	
	/**
	 * 成功响应，包含返回结果和自定义提示信息.
	 * @param data
	 * @param message
	 * @return
	 */
	public static <T> RestResponse<T> ok(T data, String message){
		RestResponse<T> ret = new RestResponse<>(data);
		ret.setMessage(message);
		return ret;
	}
	
	/**
	 * 成功响应，包含返回结果和自定义提示信息.
	 * @param message
	 * @return
	 */
	public static <T> RestResponse<T> ok(String message){
		RestResponse<T> ret = new RestResponse<>();
		ret.setMessage(message);
		return ret;
	}
	
	
	/**
	 * 失败响应，包含默认失败响应码-1和自定义失败原因提示.，
	 * @param message
	 * @return
	 */
	public static <T> RestResponse<T> fail(String message){
		return fail(-1, message);
	}
	
	/**
	 * 失败影响，包含自定义失败响应码和失败原因提示.
	 * @param code
	 * @param message
	 * @return
	 */
	public static <T> RestResponse<T> fail(int code, String message){
		RestResponse<T> ret = empty();
		ret.setCode(code);
		ret.setMessage(message);
		return ret;
	}	
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}

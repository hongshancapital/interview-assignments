package com.david.urlconverter.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

public class ResponseMessage<T> implements Serializable {
	private static final long serialVersionUID = -6204226954703695065L;

	@JsonInclude(value = JsonInclude.Include.NON_NULL)
	private T data;   //响应数据
	@JsonInclude(value = JsonInclude.Include.NON_NULL)
	private String code;  //响应数据代码
	@JsonInclude(value = JsonInclude.Include.NON_NULL)
	private String msg;   //响应代码描述
	@JsonInclude(value = JsonInclude.Include.NON_NULL)
	private String shortUrl;
	@JsonInclude(value = JsonInclude.Include.NON_NULL)
	private String longUrl;

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

	public String getLongUrl() {
		return longUrl;
	}

	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}


	public ResponseMessage() {
		super();
	}

	public ResponseMessage(T data, String code, String msg) {
		super();
		this.data = data;
		this.code = code;
		this.msg = msg;
	}
	
	public ResponseMessage(ResultStatusCode statusCode) {
		this.code = statusCode.code();
		this.msg = statusCode.msg();
	}

	public ResponseMessage(T data) {
		this.data = data;
		code = ResultStatusCode.COMMON_SUCCESS.code();
		msg = ResultStatusCode.COMMON_SUCCESS.msg();
	}

	//自定义异常返回的结果
	public static ResponseMessage defineException(UrlConverterException ie){
		ResponseMessage result = new ResponseMessage();
		result.setCode(ie.getCode());
		result.setMsg(ie.getMsg());
		return result;
	}

	public static ResponseMessage otherException(Exception e){
		ResponseMessage result = new ResponseMessage();
		result.setCode(ResultStatusCode.COMMON_FAILED.code());
		result.setMsg(e.getMessage());
		return result;
	}

	public static ResponseMessage ok() {
		ResponseMessage res = new ResponseMessage(ResultStatusCode.COMMON_SUCCESS);
		return res;
	}

	public static ResponseMessage ok(String msg) {
		ResponseMessage res = new ResponseMessage(ResultStatusCode.COMMON_SUCCESS);
		res.setMsg(msg);
		return res;
	}

	public static ResponseMessage ok(ResultStatusCode rsc) {
		ResponseMessage res = new ResponseMessage(rsc);
		return res;
	}

	public static <T> ResponseMessage<T> failed(String msg) {
		ResponseMessage res = new ResponseMessage(ResultStatusCode.COMMON_FAILED);
		res.setMsg(msg);
		return res;
	}

	public static ResponseMessage failed() {
		ResponseMessage res = new ResponseMessage(ResultStatusCode.COMMON_FAILED);
		return res;
	}

	public static ResponseMessage failed(ResultStatusCode rsc) {
		ResponseMessage res = new ResponseMessage(rsc);
		return res;
	}
}

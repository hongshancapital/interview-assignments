package com.wang.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "公用返回vo类")

public class CommResult<T> implements Serializable {

	@ApiModelProperty("0成功 1失败 401未登录或无权限")
	public int code;

	@ApiModelProperty("code为1时为错误信息内容")
	public String message;

	@ApiModelProperty("返回对象内容")
	public T content;

	public  static  Integer successCode=0;
	public  static  String successText="获取成功";
	public  static  Integer failCode=1;
	public  static  String failText="获取失败";

	public CommResult(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public CommResult(int code, String message, T content) {
		this.code = code;
		this.message = message;
		this.content = content;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}



    public static CommResult ok(Object content){
        return new CommResult(successCode,successText,content);
    }


    public static CommResult error(int code, String errormsg, Object content){
        return new CommResult(code,errormsg,content);
    }
}
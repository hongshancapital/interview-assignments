package com.sxg.shortUrl.utils;

import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author sxg
 *
 */
public class Result<T> {

	@ApiModelProperty(value = "错误码")
	private int code;
	@ApiModelProperty(value = "信息")
	private String msg;
	@ApiModelProperty(value = "内容")
	private Object data;
	@ApiModelProperty(value = "返回时间")
	private String timestamp;

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

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	// 请求处理成功，并响应结果数据
	public static <T> Result<T> success(T data) {
		Result<T> resp = new Result<>();
		resp.setCode(0);
		resp.setMsg("success");
		resp.setData(data);
		resp.setTimestamp(DateUtil.now());
		return resp;
	}

	public static <T> Result<T> error(RuntimeException e) {
		Result<T> resp = new Result<>();
		resp.setCode(-1);
		resp.setMsg(e.getMessage());
		resp.setTimestamp(DateUtil.now());
		return resp;
	}

}

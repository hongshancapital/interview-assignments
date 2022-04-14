package com.sequoiacap.model;

import java.util.Objects;

import com.sequoiacap.annotation.Generated;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author zoubin
 */
@ApiModel("Url返回值包装类")
public class UrlInfo {

	@ApiModelProperty("code")
	private int code;

	@ApiModelProperty("域名")
	private String url;

	@ApiModelProperty("错误信息")
	private String errorMsg;

	public UrlInfo() {
	}

	public UrlInfo(int code, String url, String errorMsg) {
		this.code = code;
		this.url = url;
		this.errorMsg = errorMsg;
	}

	public UrlInfo(int code, String url) {
		this(code, url, "");
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	@Generated
	@Override
	public int hashCode() {
		return Objects.hash(code, errorMsg, url);
	}

	@Generated
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UrlInfo other = (UrlInfo) obj;
		return code == other.code && Objects.equals(errorMsg, other.errorMsg) && Objects.equals(url, other.url);
	}

	@Generated
	@Override
	public String toString() {
		return "UrlInfo [code=" + code + ", url=" + url + ", errorMsg=" + errorMsg + "]";
	}

}
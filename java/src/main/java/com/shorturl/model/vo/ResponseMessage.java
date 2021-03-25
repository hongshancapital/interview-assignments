package com.shorturl.model.vo;

public class ResponseMessage {
	
	private String shortUrl;
	
	private String originUrl;
	
	private Boolean success;
	
	private String message;

	private String code;


    public ResponseMessage(String originUrl, Boolean success, String message, String code) {
        this.originUrl = originUrl;
        this.success = success;
        this.message = message;
        this.code = code;
    }

    public ResponseMessage(String shortUrl, String originUrl, Boolean success, String message) {
		this.shortUrl = shortUrl;
		this.originUrl = originUrl;
		this.success = success;
		this.message = message;
	}

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

	public String getOriginUrl() {
		return originUrl;
	}

	public void setOriginUrl(String originUrl) {
		this.originUrl = originUrl;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

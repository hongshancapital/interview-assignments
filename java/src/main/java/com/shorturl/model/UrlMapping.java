package com.shorturl.model;

import java.io.Serializable;

public class UrlMapping implements Serializable {
	
	private Long code;
	
	private String url;

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}

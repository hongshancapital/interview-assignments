package com.interview.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ShortUrlDto", description = "短域名参数对象")
public class ShortUrlDto {
	
	@ApiModelProperty(value="短域名",name="shortUrl")
	public String shortUrl;
	
	@ApiModelProperty(value="长域名",name="longUrl")
	public String longUrl;

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

	
}

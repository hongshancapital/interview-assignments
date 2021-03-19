package com.sxg.shortUrl.model;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author sxg
 *
 */
public class UrlModel {
	public UrlModel() {
	}

	public UrlModel(String shortUrl, String longUrl) {
		this.shortUrl = shortUrl;
		this.longUrl = longUrl;
	}

	@ApiModelProperty(hidden = true)
	private long id;

	@ApiModelProperty(notes = "短链接")
	private String shortUrl;

	@ApiModelProperty(notes = "长链接")
	private String longUrl;

	private Date createDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

 

}

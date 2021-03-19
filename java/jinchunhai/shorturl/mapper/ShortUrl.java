package com.jinchunhai.shorturl.mapper;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class ShortUrl {

	@Id
	@GeneratedValue(generator = "UUID")
	private String id;
	
	/**
	 * 真实的url
	 */
	@Column(name = "long_url")
	private String longUrl;
	
	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	private Date createTime;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLongUrl() {
		return longUrl;
	}

	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
package com.hongshan.taskwork.model;

public class Link {
	private String id;
	private String longUrl;
	private String shortUrl;
	private String prefixDomain;
	private String prefixDomainType;
	private String shortUrlSuffix;
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
	public String getShortUrl() {
		return shortUrl;
	}
	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}
	public String getPrefixDomain() {
		return prefixDomain;
	}
	public void setPrefixDomain(String prefixDomain) {
		this.prefixDomain = prefixDomain;
	}
	public String getPrefixDomainType() {
		return prefixDomainType;
	}
	public void setPrefixDomainType(String prefixDomainType) {
		this.prefixDomainType = prefixDomainType;
	}
	public String getShortUrlSuffix() {
		return shortUrlSuffix;
	}
	public void setShortUrlSuffix(String shortUrlSuffix) {
		this.shortUrlSuffix = shortUrlSuffix;
	}
	
}

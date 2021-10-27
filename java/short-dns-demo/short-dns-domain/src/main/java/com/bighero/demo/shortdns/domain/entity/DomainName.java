package com.bighero.demo.shortdns.domain.entity;

/**
 * 域名抽象类
 * @author bighero
 */
public abstract class DomainName {
	/**
	 * url串
	 */
	String url;	
	
	/**
	 * 域名校验功能
	 * @return
	 */
	public abstract boolean verify();
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}

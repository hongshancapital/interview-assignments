package com.bighero.demo.shortdns.domain.entity;

import java.util.regex.Pattern;

/**
 * 短域名对象
 * @author bighero
 */
public class ShortDN  extends DomainName {
	private final String BCOM="http://b.com/";
	/**
	 * 域名
	 */
	String dn;
	/**
	 * 路径
	 */
	String path;
	
	public String getDn() {
		return dn;
	}
	public void setDn(String dn) {
		this.dn = dn;
	}
	
	public ShortDN(String path) {
		this.dn=BCOM;
		this.path=path;
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String getUrl() {
		return this.BCOM+this.path;
	}

	/**
	 * 短域名校验功能
	 */
	@Override
	public boolean verify() {
		return (this.path.length()<=8);
	}

}

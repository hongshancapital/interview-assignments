package com.bighero.demo.shortdns.domain.entity;

import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

/**
 * 长域名对象
 * @author bighero
 */
@Slf4j
public class LongDN extends DomainName {
	
	private final String urlPattern="[http|https]+[://]+[0-9A-Za-z:/[-]_#[?][=][.][&]]*";
	
	public LongDN(String url) {
		this.url=url;
	}

	/**
	 * 域名校验功能
	 */
	@Override
	public boolean verify() {
		 return formatValid()&&whileListValid();
	}
	
	/**
	 * url 格式验证
	 * @return 是否成功
	 */
	private boolean formatValid() {
		log.info("长域名格式验证.");
		return Pattern.matches(urlPattern, this.url);
	}
	
	/**
	 * 白名单通过验证
	 */
	
	private boolean whileListValid() {
		log.info("白名单验证.");
		return true;
	}
	
	
	
}

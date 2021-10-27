package com.bighero.demo.shortdns.url.app.dto;

/**
 * 短域名请求DTO
 * @author bighero
 */
public class ShortDNRespDTO {
	/**
	 * 返回短域名
	 */
	String shortDN;

	public String getShortDN() {
		return shortDN;
	}
	
	public ShortDNRespDTO(String shortDN){
		this.shortDN=shortDN;
	}

}

package com.bighero.demo.shortdns.url.app.dto;

import com.bighero.demo.shortdns.domain.assmbler.IAssembly;
import com.bighero.demo.shortdns.domain.entity.DNRelEntity;
import com.bighero.demo.shortdns.domain.entity.LongDN;

/**
 * 长域名请求DTO类
 * 
 * @author bighero
 *
 */
public class LongDNReqDTO implements IAssembly<DNRelEntity> {
	/**
	 * 长域名
	 */
	String longDN;
	
	public void setLongDN(String longDN) {
		this.longDN = longDN;
	}
	
	@Override
	public DNRelEntity toAssembly(DNRelEntity newObj) {
		newObj.setLongDN(new LongDN(longDN));
		return newObj;
	}

	public String getLongDN() {
		return longDN;
	}
	
	

}

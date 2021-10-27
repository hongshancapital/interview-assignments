package com.bighero.demo.shortdns.url.app.dto;
import com.bighero.demo.shortdns.domain.assmbler.IAssembly;
import com.bighero.demo.shortdns.domain.entity.DNRelEntity;
import com.bighero.demo.shortdns.domain.entity.ShortDN;

/**
 * 短域名请求DTO类
 * @author bighero
 *
 */
public class ShortDNReqDTO  implements IAssembly<DNRelEntity>{
	/**
	 * 请求短域名
	 */
	String shortDN;

	public ShortDNReqDTO(String shortDN){
		this.shortDN=shortDN;
	}


	@Override
	public DNRelEntity toAssembly(DNRelEntity newObj) {
		newObj.setShortDN(new ShortDN(shortDN));
		return newObj;
	}
}

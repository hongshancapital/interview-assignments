package com.bighero.demo.shortdns.url.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bighero.demo.shortdns.domain.entity.DNRelEntity;
import com.bighero.demo.shortdns.domain.service.DNService;
import com.bighero.demo.shortdns.url.app.dto.LongDNReqDTO;
import com.bighero.demo.shortdns.url.app.dto.LongDNRespDTO;
import com.bighero.demo.shortdns.url.app.dto.ShortDNReqDTO;
import com.bighero.demo.shortdns.url.app.dto.ShortDNRespDTO;
import com.bighero.demo.shortdns.url.app.exception.ApplicationException;
/**
 * 应用场景服务
 * @author bighero
 */
@Service
public class DNAppService {
	
	@Autowired
	private DNService dns;
	
	/**
	 * 短域名存储接口：接受长域名信息，返回短域名信息
	 * @param reqdto 接受长域名
	 * @return ShortDNRespDTO 返回短域名信息
	 */
	public ShortDNRespDTO saveShortDN(LongDNReqDTO reqdto) {
		if(reqdto.getLongDN().trim().length()>50) {
			throw new ApplicationException("demo800002", "长域名过长请修改!");
		}
		DNRelEntity entity=dns.save(reqdto);
		return new ShortDNRespDTO(entity.getShortDN().getUrl());
	}

	/**
	 * 短域名读取接口：接受短域名信息，返回长域名信息
	 * @param path 请求路径
	 * @return LongDNRespDTO 返回长域名信息
	 */
	public LongDNRespDTO getShortDN(String path) {
		if(path.trim().length()>8) {
			throw new ApplicationException("demo800003", "路径过长请修改！");
		}
		ShortDNReqDTO reqdto=new ShortDNReqDTO(path);
		DNRelEntity entity=dns.get(reqdto);
		return new LongDNRespDTO(entity.getLongDN().getUrl());
	}
}

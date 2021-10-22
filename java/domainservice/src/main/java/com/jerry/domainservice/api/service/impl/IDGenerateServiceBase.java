package com.jerry.domainservice.api.service.impl;

import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jerry.domainservice.api.cache.CacheProvider;
import com.jerry.domainservice.api.cache.exception.CachedObjectAlreadyExistedException;
import com.jerry.domainservice.api.cache.exception.NoMatchedObjectException;
import com.jerry.domainservice.api.cache.exception.NoSpaceException;
import com.jerry.domainservice.api.exception.ServiceRejectException;
import com.jerry.domainservice.api.service.DomainInformationBO;
import com.jerry.domainservice.api.service.IDGenerateService;
import com.jerry.domainservice.api.service.util.NumberUtils;
import com.jerry.domainservice.properties.DomainServiceProperties;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IDGenerateServiceBase implements IDGenerateService {


	static final long MAX = 218340105584895L; // ZZZZZZZZ

	static final long MIN = 3521614606208L; // 10000000
	
	@Resource
	@Setter
	private CacheProvider<String,DomainInformationBO> cacheProvider;
	
	@Resource
	private DomainServiceProperties properties;
	
	@Override
	public String generate(String longDomainName) {
		
		log.trace("Generate short domain information, longDomainName={}",longDomainName);
		
		String id = null;
		
		do {
			id = generateRandomInformation();
		}while(cacheProvider.containsKey(id));
		
		DomainInformationBO information = new DomainInformationBOBase();
		information.setDomainName(longDomainName);

		try {
			cacheProvider.add(longDomainName,information);
		} catch (CachedObjectAlreadyExistedException e) {
			throw new ServiceRejectException("Domain has already been registered.",e);
		} catch (NoSpaceException e) {
			throw new ServiceRejectException("Service rejected.",e);
		}
		
		log.trace("Generate short domain information successfully, longDomainName={}, shortDomainInformation={}",longDomainName,id);
		return id;
	}
	
	
	private String generateRandomInformation() {
		Random random = new Random();
		random.longs(MIN, MAX).findFirst().getAsLong();
		return NumberUtils.convert(random.longs(MIN, MAX).findFirst().getAsLong(), 62);
	}

	@Override
	public String getDomainName(String id) {

		// 从cache中获取短域名对应的域名信息
		DomainInformationBO info;
		try {
			info = cacheProvider.get(id);
		} catch (NoMatchedObjectException e) {
			throw new ServiceRejectException("Domain name not found.",e);
		}

		return info.getDomainName();
	}
}

package com.jerry.domainservice.api.service.impl;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jerry.domainservice.api.exception.DomainExistedException;
import com.jerry.domainservice.api.exception.NoMatchedDomainException;
import com.jerry.domainservice.api.exception.ServiceRejectException;
import com.jerry.domainservice.api.service.IDGenerateService;
import com.jerry.domainservice.api.service.util.NumberUtils;
import com.jerry.domainservice.properties.DomainServiceProperties;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IDGenerateServiceImpl implements IDGenerateService {

	private ConcurrentHashMap<String, String> domainMapping = new ConcurrentHashMap<>();

	private int maxCachedSize = 0;

	static final long MAX = 218340105584895L; // ZZZZZZZZ

	static final long MIN = 3521614606208L; // 10000000
	
	@Resource
	private DomainServiceProperties properties;
	
	@PostConstruct
	public void init() {
		setMaxCachedSize(properties.getMaxCachedSize());
	}
	
	public void setMaxCachedSize(int maxCachedSize) {
		this.maxCachedSize = maxCachedSize;
	}

	@Override
	public synchronized String generate(String longDomainName) {
		
		log.trace("Generate short domain information, longDomainName={0}",longDomainName);

		if (domainMapping.size() > maxCachedSize) {
			throw new ServiceRejectException("Service Rejected.");
		}
		
		if(domainMapping.contains(longDomainName)) {
			throw new DomainExistedException("Domain name has already been registered.");
		}

		String id = generateRandomInformation();

		do {
			id = generateRandomInformation();
		} while (domainMapping.containsKey(id));
		
		domainMapping.put(id, longDomainName);
		
		log.trace("Generate short domain information successfully, longDomainName={0}, shortDomainInformation={1}",longDomainName,id);

		return id;
	}

	private String generateRandomInformation() {
		Random random = new Random();
		random.longs(MIN, MAX).findFirst().getAsLong();
		return NumberUtils.convert(random.longs(MIN, MAX).findFirst().getAsLong(), 62);
	}

	@Override
	public String getDomainName(String id) {
		if(!domainMapping.containsKey(id)) {
			throw new NoMatchedDomainException("There is no domain matched.");
		}
		return domainMapping.get(id);
	}
}

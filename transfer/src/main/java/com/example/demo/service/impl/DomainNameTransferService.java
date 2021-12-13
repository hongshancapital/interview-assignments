package com.example.demo.service.impl;

import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.example.demo.dto.Response;
import com.example.demo.service.IDomainNameTransferService;
import com.example.demo.util.Utils;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

@Service
public class DomainNameTransferService implements IDomainNameTransferService {
	private static final String SHORT_NAME_PREFIX = "https://tu.com/";
	
	private static final ConcurrentMap<Long, String> HASH64_TO_SHORTNAME_MAP = new ConcurrentHashMap<>();
	private static final ConcurrentMap<String, String> SHORTDOMAIN_TO_ORIGNAL_MAP = new ConcurrentHashMap<>();
	private static final ConcurrentMap<String, AtomicLong> SHORTDOMAIN_DOUBLE_TIME_MAP = new ConcurrentHashMap<>();
	
	/**
	 * 获得短域名
	 */
    public Response<String> getShortDomainName(String longDomainName) {  
    	if (StringUtils.isBlank(longDomainName)) {
    		return Response.fail(-1, "长域名不能为空");
    	}
    	
    	HashFunction hashFunction32 = Hashing.murmur3_32();
    	HashCode hascode32 = hashFunction32.hashString(longDomainName, Charset.forName("utf-8"));
    	
    	HashFunction hashFunction64 = Hashing.farmHashFingerprint64();
    	HashCode hascode64 = hashFunction64.hashString(longDomainName, Charset.forName("utf-8"));
    	
    	String shortDomain = getShortName(Integer.toUnsignedLong(hascode32.asInt()), hascode64.asLong(), longDomainName);
    	
   	    return Response.<String>builder()
   	    		.code(0)
   	    		.message("短域名转换成功")
   	    		.data(shortDomain)
   	    		.build();
    }
    
    /*
     * 长域名转换成短域名，规则如下：
     * 1.根据原始域名计算64位hash(8位字节最大值)，如果在HASH64_TO_SHORTNAME_MAP中找到之前计算的值，则直接返回;
     * 2.根据32位hash获得短域名前缀，分两种情况：
     *     1.如果SHORTDOMAIN_DOUBLE_TIME_MAP中不存在，则直接返回前缀，且将计算结果保存;
     *     2.如果SHORTDOMAIN_DOUBLE_TIME_MAP中存在，则将碰撞次数+1，并根据碰撞次数技术短域名的剩余部分。
     */
    private String getShortName(long hasCode32, long hasCode64, String originalUrl) {
    	String shortName = HASH64_TO_SHORTNAME_MAP.get(hasCode64);
    	
    	if (StringUtils.isNotBlank(shortName)) {
    		return SHORT_NAME_PREFIX + shortName;
    	} else {
    		String shortNameSuffix = Utils.numToStr(hasCode32);
    		
    		AtomicLong atomicLong = null;
        	synchronized (SHORTDOMAIN_DOUBLE_TIME_MAP) {
        		atomicLong = SHORTDOMAIN_DOUBLE_TIME_MAP.get(shortNameSuffix);
        		
        		if (atomicLong == null) {
        			SHORTDOMAIN_DOUBLE_TIME_MAP.put(shortNameSuffix, new AtomicLong(0));
        			SHORTDOMAIN_TO_ORIGNAL_MAP.put(shortNameSuffix, originalUrl);
        			HASH64_TO_SHORTNAME_MAP.put(hasCode64, shortNameSuffix);
        			return SHORT_NAME_PREFIX + shortNameSuffix;
        		}
        	}
        	
        	long maxTimes = (2 ^ ((8 - shortNameSuffix.length()) * 6));
        	synchronized (atomicLong) {
        		long count = atomicLong.get();
        		if (maxTimes >= atomicLong.get()) {
        			count = 0;
        		} else {
        			count++;
        		}
        		
        		atomicLong.set(count);
        		
        		shortName = shortNameSuffix + (count == 0 ? "" :Utils.numToStr(count));
        		
        		HASH64_TO_SHORTNAME_MAP.put(hasCode64, shortName);
        		SHORTDOMAIN_TO_ORIGNAL_MAP.put(shortName, originalUrl);
        		
        		return SHORT_NAME_PREFIX + shortName;
        	}	
    	}
    }
    
	/**
	 * 获得长域名
	 */
    public Response<String> getLongDomainName(String shortDomainName) {
    	if (StringUtils.isBlank(shortDomainName)) {
    		return Response.fail(-1, "短域名不能为空");
    	}
    	
    	if (StringUtils.isNotBlank(shortDomainName) && shortDomainName.startsWith(SHORT_NAME_PREFIX)) {
    		shortDomainName = shortDomainName.replaceFirst(SHORT_NAME_PREFIX, ""); 
    	}
    	
   	    if (SHORTDOMAIN_TO_ORIGNAL_MAP.containsKey(shortDomainName)) {
   	    	return Response.<String>builder()
   	    			.code(0)
   	    			.message("长域名查询成功")
   	    			.data(SHORTDOMAIN_TO_ORIGNAL_MAP.get(shortDomainName))
   	    			.build();  	    	
   	    } else {
   	    	return Response.<String>builder()
   	    			.code(-1)
   	    			.message("长域名查询失败")
   	    			.build();
   	    }
    }
}

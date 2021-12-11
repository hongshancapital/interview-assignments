package com.example.demo.service.impl;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	
	private static final Map<String, String> MAP = new HashMap<>();
	private static final ConcurrentMap<String, List<BigDecimal>> MAP_LIST = new ConcurrentHashMap<>();
	
    public Response<String> getShortDomainName(String longDomainName) {
    	HashFunction hashFunction = Hashing.murmur3_32();
    	HashCode hascode = hashFunction.hashString(longDomainName, Charset.forName("utf-8"));
    	
    	HashFunction hashFunction64 = Hashing.farmHashFingerprint64();
    	HashCode hascode64 = hashFunction64.hashString(longDomainName, Charset.forName("utf-8"));
    	BigDecimal bigDecimal = new BigDecimal(Long.toUnsignedString(hascode64.asLong()));
    	
    	//int count = getCollisionCount(hascode.toString(), bigDecimal);
    	// 暂没考虑碰撞
    	int count = 0;
    	
    	String shortDomain = Utils.numToStr(hascode.asInt(), 6) + Utils.numToStr(count, 2);
    	
    	MAP.put(shortDomain, longDomainName);
    	
   	    return Response.<String>builder()
   	    		.code(0)
   	    		.message("短域名转换成功")
   	    		.data(SHORT_NAME_PREFIX + shortDomain)
   	    		.build();
    }
    
/*    private synchronized String getCollisionCount(String hascode32, BigDecimal hascode128) {
    	List<BigDecimal> list = MAP_LIST.get(hascode32);
    	if (list == null) {
    		list = new ArrayList<>();
    		list.add(hascode128);
    		MAP_LIST.put(hascode32, list);
    		return "00";
    	} else {
    		int index = list.indexOf(hascode128);
    		if (index >= 0) {
    			return index;
    		} else {
    			list.add(hascode128);
    			return list.size() - 1;
    		}
    	}
    }*/
    
    public Response<String> getLongDomainName(String shortDomainName) {
    	if (StringUtils.isNotBlank(shortDomainName) && shortDomainName.startsWith(SHORT_NAME_PREFIX)) {
    		shortDomainName = shortDomainName.replaceFirst(SHORT_NAME_PREFIX, ""); 
    	}
    	
   	    if (MAP.containsKey(shortDomainName)) {
   	    	return Response.<String>builder()
   	    			.code(0)
   	    			.message("长域名查询成功")
   	    			.data(MAP.get(shortDomainName))
   	    			.build();  	    	
   	    } else {
   	    	return Response.<String>builder()
   	    			.code(-1)
   	    			.message("长域名查询失败")
   	    			.build();
   	    }
    }
}

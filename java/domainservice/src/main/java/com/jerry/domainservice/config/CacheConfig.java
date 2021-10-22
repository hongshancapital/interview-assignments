package com.jerry.domainservice.config;

import java.util.Timer;
import java.util.TimerTask;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jerry.domainservice.api.cache.CacheProvider;
import com.jerry.domainservice.api.cache.impl.HashMapCacheProvider;
import com.jerry.domainservice.api.cache.properties.HashMapCacheProperties;
import com.jerry.domainservice.api.service.DomainInformationBO;
import com.jerry.domainservice.properties.DomainServiceProperties;

@Configuration
public class CacheConfig {
	@Bean
	public CacheProvider<String,DomainInformationBO> cacheProvider(DomainServiceProperties properties) {
		HashMapCacheProvider<String,DomainInformationBO> provider = new HashMapCacheProvider<>();
		HashMapCacheProperties hcp = properties.getHashmapcache();
		provider.setNumberShouldBeEvicted(hcp.getNumberShouldBeEvicted());
		provider.setMaxCacheSize(hcp.getMaxCacheSize());
		provider.setSurvivePeriod(hcp.getSurvivePeriod());
		
		// 每秒执行下evict
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				provider.evict();
			}
			
		}, 1000L,1000L);
		
		return provider;
	}
}

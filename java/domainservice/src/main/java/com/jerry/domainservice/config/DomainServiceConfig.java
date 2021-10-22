package com.jerry.domainservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.jerry.domainservice.api.cache.CacheProvider;
import com.jerry.domainservice.api.cache.impl.HashMapCacheProvider;
import com.jerry.domainservice.api.service.DomainInformationBO;
import com.jerry.domainservice.properties.DomainServiceProperties;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@ComponentScan({ "com.jerry.domainservice.api" })
@EnableConfigurationProperties({ DomainServiceProperties.class })
public class DomainServiceConfig {

	/**
	 * Open API document configurations.
	 * 
	 * @param appVersion
	 * @return
	 */
	@Bean
	public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {
		return new OpenAPI().components(new Components())
				.info(new Info().title("Domain Service API").version(appVersion).description(
						"This is the RESTful service of Domain Service using springdoc-openapi and OpenAPI 3."));
	}
}

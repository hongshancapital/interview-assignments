package com.jerry.domainservice.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.jerry.domainservice.api.cache.properties.HashMapCacheProperties;

import lombok.Data;

/**
 * Defines all of the properties that used in domain service project.
 * @author jerry
 *
 */
@ConfigurationProperties(prefix="domainservice")
@Data
public class DomainServiceProperties {
	HashMapCacheProperties hashmapcache;
}

package com.jerry.domainservice.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * Defines all of the properties that used in domain service project.
 * @author jerry
 *
 */
@ConfigurationProperties(prefix="domainservice")
@Data
public class DomainServiceProperties {
	/**
	 * Maximum size of the domain cache. if exceeded then the server will throw a ServiceRejectException.
	 */
	private int maxCachedSize;
}

package com.jerry.domainservice.api.service;

public interface IDGenerateService {
	/**
	 * Generate the domain ID for the input long domain.
	 * @param longDomainName
	 * @return
	 */
	String generate(String longDomainName);

	/**
	 * Retrieve the domain name by the input ID.
	 * @param id
	 * @return
	 */
	String getDomainName(String id);
}

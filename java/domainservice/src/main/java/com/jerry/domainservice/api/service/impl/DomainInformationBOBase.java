package com.jerry.domainservice.api.service.impl;

import com.jerry.domainservice.api.service.DomainInformationBO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class DomainInformationBOBase implements DomainInformationBO {

	/**
	 * Domain name
	 */
	private String domainName;
	
	/**
	 * Time in milliseconds of getting domain name by short domain information.
	 */
	private Long lastUpdateTime;
	
	/**
	 * Position of the domain information in a list.
	 */
	private Integer position;
}

package com.jerry.domainservice.api.controller;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.jerry.domainservice.api.exception.ValidateException;
import com.jerry.domainservice.api.model.GenerateRequestDto;

@Component
public class GenerateRequestDtoValidator implements Validator<GenerateRequestDto> {

	@Override
	public void validate(GenerateRequestDto body) {
		if(StringUtils.isEmpty(body.getDomainName())) {
			throw new ValidateException("Doamin name is required.");
		}
		if(body.getDomainName().length()>255) {
			throw new ValidateException("Maximum length of domain name is 255.");
		}
		if(!Pattern.matches("^[a-zA-Z0-9\\.]+$", body.getDomainName())) {
			throw new ValidateException("Domain name only support alphabet and 0-9 numeric.");
		}
		
	}

}

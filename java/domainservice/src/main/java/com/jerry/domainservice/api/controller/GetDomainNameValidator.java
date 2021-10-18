package com.jerry.domainservice.api.controller;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.jerry.domainservice.api.exception.ValidateException;

@Component
public class GetDomainNameValidator implements Validator<String> {

	@Override
	public void validate(String body) {
		if(StringUtils.isEmpty(body)) {
			throw new ValidateException("Short domain information is required.");
		}
		if(body.length()!=8) {
			throw new ValidateException("Short domain information must be a 8 length character.");
		}
		if(!Pattern.matches("^[a-zA-Z0-9]+$", body)) {
			throw new ValidateException("Short domain information only support alphabet and 0-9 numeric.");
		}
	}

}

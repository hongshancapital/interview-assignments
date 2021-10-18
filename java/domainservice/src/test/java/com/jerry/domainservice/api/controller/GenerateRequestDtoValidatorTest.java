package com.jerry.domainservice.api.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jerry.domainservice.api.exception.ValidateException;
import com.jerry.domainservice.api.model.GenerateRequestDto;

@ExtendWith(MockitoExtension.class)
class GenerateRequestDtoValidatorTest {

	@InjectMocks
	GenerateRequestDtoValidator validator;
	
	@Test
	void testValidate() {
		GenerateRequestDto dto = new GenerateRequestDto();
		dto.setDomainName("");
		Assertions.assertThrows(ValidateException.class, ()->validator.validate(dto));
		
		dto.setDomainName("12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890.com");
		Assertions.assertThrows(ValidateException.class, ()->validator.validate(dto));
		
		dto.setDomainName("asdf{.com");
		Assertions.assertThrows(ValidateException.class, ()->validator.validate(dto));
		
		dto.setDomainName("123");
		Assertions.assertDoesNotThrow(()->validator.validate(dto));
	}

}

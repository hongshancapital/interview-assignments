package com.jerry.domainservice.api.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jerry.domainservice.api.exception.ValidateException;

@ExtendWith(MockitoExtension.class)
class GetDomainNameValidatorTest {
	

	@InjectMocks
	GetDomainNameValidator validator;

	@Test
	void testValidate() {
		Assertions.assertThrows(ValidateException.class, ()->validator.validate(""));
		
		Assertions.assertThrows(ValidateException.class, ()->validator.validate("123412341"));
		
		Assertions.assertThrows(ValidateException.class, ()->validator.validate("12341{41"));
		
		Assertions.assertDoesNotThrow(()->validator.validate("12345678"));
	}

}

package com.jerry.domainservice.api.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jerry.domainservice.api.exception.DomainExistedException;
import com.jerry.domainservice.api.exception.NoMatchedDomainException;
import com.jerry.domainservice.api.exception.ServiceRejectException;

@ExtendWith(MockitoExtension.class)
class IDGenerateServiceImplTest {

	@InjectMocks
	IDGenerateServiceImpl service;
	
	@BeforeEach
	void setup() {
		service.setMaxCachedSize(100);
	}
	
	@Test
	void testGenerate() {
		Assertions.assertDoesNotThrow(()->service.generate("www.baidu.com"));
	}
	@Test
	void testGetDomainName() {
		service.setMaxCachedSize(1000);
		String id = service.generate("www.baidu.com");
		Assertions.assertEquals(service.getDomainName(id),"www.baidu.com");
	}
	
	@Test
	void testGetDomainNameWithNoMatchedDomainException() {
		Assertions.assertThrows(NoMatchedDomainException.class,()->service.getDomainName("a912kxDc"));
	}
	
	@Test
	void testGenerateWithServiceRejectException() {
		Assertions.assertThrows(ServiceRejectException.class,()->{
			int number = 0;
			while(true) {
				service.generate("testDomain"+number+++".test");
			}
		});
	}
	
	@Test
	void testGenerateWithDomainExistedException() {
		Assertions.assertThrows(DomainExistedException.class,()->{
			service.generate("testDomain1test");
			service.generate("testDomain1test");
		});
	}

}

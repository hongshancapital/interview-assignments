package com.jerry.domainservice.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.jerry.domainservice.api.exception.NoMatchedDomainException;
import com.jerry.domainservice.api.exception.ServiceRejectException;
import com.jerry.domainservice.api.model.GenerateRequestDto;
import com.jerry.domainservice.api.model.GenerateResponseDto;
import com.jerry.domainservice.api.model.GetResponseDto;
import com.jerry.domainservice.api.service.IDGenerateService;

@ExtendWith(MockitoExtension.class)
class DomainApiControllerTest {

	@InjectMocks
	DomainApiController controller;

	@Mock
	IDGenerateService service;


	@Test
	void testPutDomainGenerate() {
		
		doReturn("axB81Cwz").when(service).generate(any());
		doThrow(ServiceRejectException.class).when(service).generate("error.com");

		GenerateRequestDto dto = new GenerateRequestDto();
		dto.setDomainName("www.testsite.com");
		ResponseEntity<GenerateResponseDto> resopnse = controller.putDomainGenerate(dto);
		Assertions.assertTrue(resopnse.getBody().isResult());

		dto.setDomainName("error.com");
		resopnse = controller.putDomainGenerate(dto);
		Assertions.assertFalse(resopnse.getBody().isResult());
	}

	@Test
	void testGetDomainGet() {
		
		doReturn("www.testsite.com").when(service).getDomainName(any());
		doThrow(NoMatchedDomainException.class).when(service).getDomainName("error123");
		
		ResponseEntity<GetResponseDto> resopnse = controller.getDomainGet("axB81Cwz");
		Assertions.assertTrue(resopnse.getBody().isResult());
		
		resopnse = controller.getDomainGet("error123");
		Assertions.assertFalse(resopnse.getBody().isResult());
	}

}

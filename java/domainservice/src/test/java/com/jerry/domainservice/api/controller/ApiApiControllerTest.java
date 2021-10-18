package com.jerry.domainservice.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.jerry.domainservice.api.model.GenerateRequestDto;
import com.jerry.domainservice.api.model.GenerateResponseDto;
import com.jerry.domainservice.api.model.GetResponseDto;
import com.jerry.domainservice.api.service.IDGenerateService;
import com.jerry.domainservice.common.dto.ResultEnum;

@ExtendWith(MockitoExtension.class)
class ApiApiControllerTest {

	@InjectMocks
	ApiApiController controller;

	@Mock
	IDGenerateService service;
	
	@Mock
	GenerateRequestDtoValidator generateRequestDtoValidator;
	
	@Mock
	GetDomainNameValidator getDomainNameValidator;

	@Test
	void testPutDomainGenerate() {
		
		doReturn("axB81Cwz").when(service).generate(any());

		GenerateRequestDto dto = new GenerateRequestDto();
		dto.setDomainName("www.testsite.com");
		ResponseEntity<GenerateResponseDto> resopnse = controller.putDomainGenerate(dto);
		Assertions.assertEquals(resopnse.getBody().getStatus(), ResultEnum.SUCCESS.getCode());
	}

	@Test
	void testGetDomainGet() {
		
		doReturn("www.testsite.com").when(service).getDomainName(any());
		
		ResponseEntity<GetResponseDto> resopnse = controller.getDomainGet("axB81Cwz");
		Assertions.assertEquals(resopnse.getBody().getStatus(), ResultEnum.SUCCESS.getCode());
		
	}

}

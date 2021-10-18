package com.jerry.domainservice.api.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.jerry.domainservice.api.ApiApi;
import com.jerry.domainservice.api.model.DomainDto;
import com.jerry.domainservice.api.model.GenerateRequestDto;
import com.jerry.domainservice.api.model.GenerateResponseDto;
import com.jerry.domainservice.api.model.GetResponseDto;
import com.jerry.domainservice.api.model.ShortDomainInformationDto;
import com.jerry.domainservice.api.service.IDGenerateService;
import com.jerry.domainservice.common.dto.ResultEnum;

@RestController
public class ApiApiController implements ApiApi {

	@Resource
	private IDGenerateService service;

	@Resource
	private GenerateRequestDtoValidator generateRequestDtoValidator;

	@Resource
	private GetDomainNameValidator getDomainNameValidator;

	@Override
	public ResponseEntity<GenerateResponseDto> putDomainGenerate(@Valid GenerateRequestDto body) {
		generateRequestDtoValidator.validate(body);
		GenerateResponseDto response = new GenerateResponseDto();
		response.setStatus(ResultEnum.SUCCESS.getCode());
		
		ShortDomainInformationDto dto = new ShortDomainInformationDto();
		dto.setShortDomainInformation(service.generate(body.getDomainName()));
		response.setData(dto);
		return ResponseEntity.ok().body(response);
	}

	@Override
	public ResponseEntity<GetResponseDto> getDomainGet(String shortDomainInformation) {
		getDomainNameValidator.validate(shortDomainInformation);
		GetResponseDto response = new GetResponseDto();
		response.setStatus(ResultEnum.SUCCESS.getCode());
		
		DomainDto dto = new DomainDto();
		dto.setDomainName(service.getDomainName(shortDomainInformation));
		response.setData(dto);
		return ResponseEntity.ok().body(response);
	}

}

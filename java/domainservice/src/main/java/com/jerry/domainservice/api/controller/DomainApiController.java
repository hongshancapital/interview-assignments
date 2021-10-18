package com.jerry.domainservice.api.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.jerry.domainservice.api.DomainApi;
import com.jerry.domainservice.api.model.DomainDto;
import com.jerry.domainservice.api.model.GenerateRequestDto;
import com.jerry.domainservice.api.model.GenerateResponseDto;
import com.jerry.domainservice.api.model.GetResponseDto;
import com.jerry.domainservice.api.model.ShortDomainInformationDto;
import com.jerry.domainservice.api.service.IDGenerateService;

@RestController
public class DomainApiController implements DomainApi {

	@Resource
	private IDGenerateService service;

	@Override
	public ResponseEntity<GenerateResponseDto> putDomainGenerate(@Valid GenerateRequestDto body) {

		GenerateResponseDto response = new GenerateResponseDto();
		response.setResult(true);
		try {
			ShortDomainInformationDto dto = new ShortDomainInformationDto();
			dto.setShortDomainInformation(service.generate(body.getDomainName()));
			response.setData(dto);
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setResult(false);
		}
		return ResponseEntity.ok().body(response);
	}

	@Override
	public ResponseEntity<GetResponseDto> getDomainGet(String shortDomainInformation) {

		GetResponseDto response = new GetResponseDto();
		response.setResult(true);
		try {
			DomainDto dto = new DomainDto();
			dto.setDomainName(service.getDomainName(shortDomainInformation));
			response.setData(dto);
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setResult(false);
		}
		return ResponseEntity.ok().body(response);
	}

}

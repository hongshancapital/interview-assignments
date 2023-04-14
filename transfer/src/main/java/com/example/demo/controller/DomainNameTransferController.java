package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.Response;
import com.example.demo.service.IDomainNameTransferService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/trans")
@Api(tags="1.0", value="域名转换")
public class DomainNameTransferController {
	@Autowired
	private IDomainNameTransferService domainNameTransferService;
	
	@PostMapping("/getShortDomainName")
	@ApiOperation(value="长域名转短域名")
	@ApiImplicitParams(@ApiImplicitParam(name="longDomainName", value="长域名",
	       dataType = "String"))
     public Response<String> getShortDomainName(String longDomainName) {
		log.info("查询短域名：{}", longDomainName);
		return domainNameTransferService.getShortDomainName(longDomainName);
     }
     
	@PostMapping("/getLongDomainName")
	@ApiOperation(value="短域名转长域名")
	@ApiImplicitParams(@ApiImplicitParam(name="shortDomainName", value="短域名",
	       dataType = "String"))
     public Response<String> getLongDomainName(String shortDomainName) {
		log.info("查询长域名：{}", shortDomainName);
		return domainNameTransferService.getLongDomainName(shortDomainName);
     }
}

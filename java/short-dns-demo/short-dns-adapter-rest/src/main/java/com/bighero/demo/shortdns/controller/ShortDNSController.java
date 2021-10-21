package com.bighero.demo.shortdns.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bighero.demo.shortdns.domain.exception.AdapterException;
import com.bighero.demo.shortdns.exception.RestAdapterException;
import com.bighero.demo.shortdns.messages.ReqMsg;
import com.bighero.demo.shortdns.messages.RespMsg;
import com.bighero.demo.shortdns.messages.RespMsg.RespMsgBuilder;
import com.bighero.demo.shortdns.url.app.dto.LongDNReqDTO;
import com.bighero.demo.shortdns.url.app.dto.LongDNRespDTO;
import com.bighero.demo.shortdns.url.app.dto.ShortDNRespDTO;
import com.bighero.demo.shortdns.url.app.service.DNAppService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * restful设施服务
 * 
 * @author bighero
 *
 */
@Api("短域名服务接口")
@RequestMapping("/shortdns")
@RestController
@Slf4j
public class ShortDNSController {

	@Autowired
	private DNAppService appService;

	@ApiOperation("存储短域名接口")
	@RequestMapping(method = RequestMethod.POST, value = "/url")
	public RespMsg<?> saveShortUrl(@RequestBody ReqMsg<LongDNReqDTO> reqMsg) {
		if (reqMsg.data == null||StringUtils.isEmpty(reqMsg.data.getLongDN().trim()))
			throw new RestAdapterException("demo800001", "长域名输入不能为空");
		ShortDNRespDTO shortDNRespDTO = appService.saveShortDN(reqMsg.data);
		 RespMsgBuilder<?> respMsgBuilder=RespMsg.builder().appCode("demo00001").appMsg("存储短域名成功").appStatus("1").sysCode(HttpStatus.OK.value())
		.sysMsg(HttpStatus.OK.name()).sysStatus("1").msgBody(shortDNRespDTO);
		log.info(respMsgBuilder.toString());
		return respMsgBuilder.build();

	}

	@ApiOperation("读取短域名接口")
	@RequestMapping(method = RequestMethod.GET, value = "/url/{path}")
	public RespMsg<?> getShortDN(@PathVariable String path) {
		if (StringUtils.isEmpty(path))
			throw new RestAdapterException("demo800002", "短域名输入不能为空");
		LongDNRespDTO longDNRespDTO = appService.getShortDN(path);
		return RespMsg.builder().appCode("demo00002").appMsg("读取短域名成功").appStatus("1").sysCode(HttpStatus.OK.value())
				.sysMsg(HttpStatus.OK.name()).sysStatus("1").msgBody(longDNRespDTO).build();

	}
}

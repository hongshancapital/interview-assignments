package com.scdtchina.controller;

import com.scdtchina.service.IShortDomainNameService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hai.yuan
 */
@RestController
@Api(value = "短域名服务")
public class ShortDomainNameController {

    private static final Logger log = LoggerFactory.getLogger(ShortDomainNameController.class);

    @Autowired
    private IShortDomainNameService iShortDomainNameService;

    @ApiOperation(value = "短域名存储接口",notes = "接受长域名信息，返回短域名信息")
    @GetMapping("/sdn")
    public ResponseEntity<Object> getShortDomainName(@RequestParam(required = true) @ApiParam("长域名") String longDomainName) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(iShortDomainNameService.generateShortDomainName(longDomainName));
        } catch (Exception e) {
            log.error("获取短域名失败",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @ApiOperation(value = "短域名读取接口",notes = "接受短域名信息，返回长域名信息")
    @GetMapping("/ldn")
    public ResponseEntity<Object> getLongDomainName(@RequestParam(required = true) @ApiParam("短域名") String shortDomainName) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(iShortDomainNameService.obtainLongDomainName(shortDomainName));
        } catch (Exception e) {
            log.error("获取短域名失败",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}

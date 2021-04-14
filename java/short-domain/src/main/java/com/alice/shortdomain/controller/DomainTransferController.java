package com.alice.shortdomain.controller;

import com.alice.shortdomain.dto.RequestDTO;
import com.alice.shortdomain.dto.ResponseDTO;
import com.alice.shortdomain.service.DomainTransferService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 长域名转短域名，根据短域名查询长域名
 *
 * @author Alice [l1360737271@qq.com]
 * @date 2021/4/13 17:35
 */
@RestController
@Api(tags = "域名处理模块")
@RequestMapping("domain")
public class DomainTransferController {

    private final DomainTransferService domainTransferService;

    public DomainTransferController(DomainTransferService domainTransferService) {
        this.domainTransferService = domainTransferService;
    }


    @PostMapping("transfer-short")
    @ApiOperation(value = "长域名转短域名")
    public ResponseDTO<String> transferShort(RequestDTO request) {
        return domainTransferService.transferShort(request);
    }

    @PostMapping("search")
    @ApiOperation(value = "根据短域名查询长域名")
    public ResponseDTO<String> search(RequestDTO request) {
        return domainTransferService.search(request);
    }

}

package com.yujianfei.controller;

import com.yujianfei.entity.LongDNReqDTO;
import com.yujianfei.entity.LongDNRespDTO;
import com.yujianfei.entity.RespMsg;
import com.yujianfei.entity.RespMsg.RespMsgBuilder;
import com.yujianfei.entity.ShortDNRespDTO;
import com.yujianfei.service.IDNAppService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("短域名服务接口")
@RestController
@RequestMapping("/shortdn")
public class ShortDNController {

    @Autowired
    private IDNAppService dnAppService;

    @ApiOperation("存储短域名接口")
    @PostMapping("/save")
    public RespMsg<?> saveShortUrl(@RequestBody LongDNReqDTO reqMsg) {
        ShortDNRespDTO shortDNRespDTO = dnAppService.saveShortDN(reqMsg);
        RespMsgBuilder<?> respMsgBuilder = RespMsg.builder().appCode("2000").appMsg("存储短域名成功")
                .appStatus("1").msgBody(shortDNRespDTO);
        return respMsgBuilder.build();
    }

    @ApiOperation("获取长域名接口")
    @GetMapping("/url/{path}")
    public RespMsg<?> getShortDn(@PathVariable String path) {
        LongDNRespDTO longDNRespDTO = dnAppService.getLongDN(path);
        RespMsgBuilder<?> respMsgBuilder = RespMsg.builder().appCode("2000").appMsg("读取短域名成功")
                .appStatus("1").msgBody(longDNRespDTO);
        return respMsgBuilder.build();
    }

}

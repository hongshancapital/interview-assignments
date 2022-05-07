package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.common.ResultCode;
import com.example.demo.service.StorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author dsm
 * @since 2021-12-23
 */
@RestController
@Api(value="域名服务", tags="域名服务")
@RequestMapping("/areaNameController")
public class AreaNameController {

    @Resource
    private StorageService storageService;

    @PostMapping(value="/areaNameStorag")
    @ApiOperation(value="短域名存储", notes="短域名存储")
    public Result <String> AreaNameStorage( @RequestParam("areaName") String areaName ) {
        Result <String> result=new Result <String>();
        String resUrl=this.storageService.AreaNameStorage(areaName);
        result.setResult(ResultCode.SUCCESS.isSuccess(), ResultCode.SUCCESS.getCode(), resUrl);
        return result;
    }

    @PostMapping(value="/areaNameRead")
    @ApiOperation(value="短域名读取", notes="短域名读取")
    public Result <String> AreaNameRead( @RequestParam("areaName") String areaName ) {
        Result <String> result=new Result <String>();
        String resUrl=this.storageService.AreaNameRead(areaName);
        result.setResult(ResultCode.SUCCESS.isSuccess(), ResultCode.SUCCESS.getCode(), resUrl);
        return result;
    }

}


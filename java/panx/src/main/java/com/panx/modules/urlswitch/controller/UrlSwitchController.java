package com.panx.modules.urlswitch.controller;

import com.panx.model.Result;
import com.panx.modules.urlswitch.service.UrlSwitchService;
import com.panx.modules.urlswitch.vo.UrlVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author PanX
 * @date 2021-07-01
 * 长短域名转化Controller
 *
 */
@RestController
@RequestMapping("/urlswitch")
@Api(tags = "Url Switch 长短域名转化")
public class UrlSwitchController {
    @Autowired
    private UrlSwitchService urlSwitchService;

    /**
     * 短域名转为长域名
     * @Param 包含长域名的UrlVo urlInfo
     * @return 包含结果信息及接口运行信息的Result
     */
    @ApiOperation(value = "短域名转为长域名")
    @PostMapping(value ="/urlShortToLong")
    public Result urlShortToLong(@RequestBody UrlVo urlInfo){
        try{
            return Result.succeed(urlSwitchService.urlShortToLong(urlInfo),"成功");
        }catch (Exception e){
            return Result.failed(urlInfo, e.getMessage());
        }
    }

    /**
     * 长域名转为短域名
     * @Param 包含短域名的UrlVo urlInfo
     * @return 包含结果信息及接口运行信息的Result
     */
    @ApiOperation(value = "长域名转为短域名")
    @PostMapping(value = "/urlLongToShort")
    public Result urlLongToShort(@RequestBody UrlVo urlInfo){
        try {
            return Result.succeed(urlSwitchService.urlLongToShort(urlInfo), "成功");
        }catch (Exception e){
            return Result.failed(urlInfo, e.getMessage());
        }
    }

    /**
     * 自动判断并转换长短域名
     * @Param 包含长域名或短域名的UrlVo urlInfo
     * @return 包含结果信息及接口运行信息的Result
     */
    @ApiOperation(value = "自动判断并转换长短域名")
    @PostMapping(value = "/urlSwitch")
    public Result urlSwitch(@RequestBody UrlVo urlInfo){
        try {
            return Result.succeed(urlSwitchService.urlSwitch(urlInfo), "成功");
        }catch (Exception e){
            return Result.failed(urlInfo, e.getMessage());
        }
    }
}

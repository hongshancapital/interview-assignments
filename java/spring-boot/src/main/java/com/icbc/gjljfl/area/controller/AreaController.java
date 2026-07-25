package com.icbc.gjljfl.area.controller;

import com.alibaba.fastjson.JSONObject;
import com.icbc.gjljfl.common.ResponseEntity;
import com.icbc.gjljfl.area.service.AreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "实现短域名服务")
@RestController
@RequestMapping("/v1/area")
public class AreaController {



     @Autowired
    private AreaService areaService;

    //短域名存储接口：接受长域名信息，返回短域名信息
    @PostMapping("/saveUrl")
    @ApiOperation(value = "短域名存储接口：接受长域名信息，返回短域名信息", notes = "短域名存储接口：接受长域名信息，返回短域名信息", httpMethod = "POST")
    public ResponseEntity saveUrl(String url) {
        return areaService.saveUrl(url);
    }

    //短域名读取接口：接受短域名信息，返回长域名信息
    @PostMapping("/readUrl")
    @ApiOperation(value = "短域名读取接口：接受短域名信息，返回长域名信息", notes = "短域名读取接口：接受短域名信息，返回长域名信息", httpMethod = "POST")
    public ResponseEntity readUrl(String url) {
        return areaService.readUrl(url);
    }

//     /*
//     查询全部省级code，name
//      */
//    @PostMapping("/queryProvinceList")
//    public ResponseEntity queryProvinceList(@RequestBody JSONObject jsonObject){
//        return areaService.queryProvinceList();
//    }
//
//    /*
//    查询市级code，name
//     */
//    @PostMapping("/queryCityByProvinceCode")
//    public ResponseEntity queryCityByProvinceCode(@RequestBody JSONObject jsonObject){
//        return areaService.queryCityByProvinceCode(jsonObject);
//    }
///*
//    查询区级code，name
//     */
//    @PostMapping("/queryAreaByCityCode")
//    public ResponseEntity queryAreaByCityCode(@RequestBody JSONObject jsonObject){
//        return areaService.queryAreaByCityCode(jsonObject);
//    }
///*
//    我的地址修改/新增
//     */
//    @PostMapping("/mine/addressUpdate")
//    public ResponseEntity addressUpdate(@RequestBody JSONObject jsonObject){
//        return areaService.addressUpdat(jsonObject);
//    }
//
//    /*
//    我的地址查询
//     */
//    @PostMapping("/mine/queryUserAddress")
//    public ResponseEntity queryUserAddress(@RequestBody JSONObject jsonObject){
//        return areaService.queryUserAddress(jsonObject);
//    }
//    /*
//    查询街道code，name
//     */
//    @PostMapping("/queryCountyByArea")
//    public ResponseEntity queryCountyByArea(@RequestBody JSONObject jsonObject){
//        return areaService.queryCountyByArea(jsonObject);
//    }
//    /*
//    查询社区code，name
//     */
//    @PostMapping("/queryAreaByTownCode")
//    public ResponseEntity queryAreaByTownCode(@RequestBody JSONObject jsonObject){
//        return areaService.queryAreaByTownCode(jsonObject);
//    }
}

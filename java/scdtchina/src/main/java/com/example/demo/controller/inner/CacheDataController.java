package com.example.demo.controller.inner;

import com.alibaba.fastjson.JSON;
import com.example.demo.core.web.ApiResponse;
import com.example.demo.dao.CacheDB;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName CacheDataController
 * @Description 获取缓存数据相关接口
 * @Author gongguanghui
 * @Date 2021/11/26 12:01 PM
 * @Version 1.0
 **/
@Api("缓存数据相关接口文档")
@RestController
@RequestMapping("/cache")
public class CacheDataController {
    @Autowired
    private CacheDB cacheDB;

    @ApiOperation(value = "获取缓存中的数据", notes = "获取缓存中的数据")
    @GetMapping("/getAllCacheDatas")
    public ApiResponse getAllCacheDatas() {
        ApiResponse response = ApiResponse.buildSuccess();
        response.setData(JSON.toJSONString(cacheDB.getAll().keySet()));
        return response;
    }
    @ApiOperation(value = "获取缓存中的数据数量", notes = "获取缓存中的数据数量")
    @GetMapping("/getSize")
    public ApiResponse getSize() {
        ApiResponse response = ApiResponse.buildSuccess();
        response.setData(cacheDB.getAll().size());
        return response;
    }
}

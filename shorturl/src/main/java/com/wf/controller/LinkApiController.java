package com.wf.controller;

import com.wf.services.LinkApiService;
import com.wf.services.impl.LinkApiServiceImpl;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

import static com.wf.services.impl.LinkApiServiceImpl.lMap;


@Api(value = "API - LinkApiController", description = "长短链接互转API")
@RestController
@RequestMapping("/api")
public class LinkApiController {


    @ApiOperation(value = "查询所有链接对", notes = "此接口获取所有链接对", response = String.class)

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public  Map<String, String> index() {

        if (! lMap.isEmpty()) {
            return lMap;
        } else {
            return null;
        }
    }



    @ApiOperation(value = "长链接生成短链接", notes = "长链接生成短链接", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )

    @RequestMapping( value="/toshort/{linkstr}", method = RequestMethod.GET)
    public Map<String, Object> toShort(@PathVariable String linkstr){

        LinkApiService lk = new LinkApiServiceImpl();  // 实例化Service
        String str = lk.toShort(linkstr); // 长链接转短链接

        // 构造JSON输出
        Map<String, Object> map = new HashMap<>();
        map.put("url", linkstr);
        map.put("short", str);

        return map;
    }



    @ApiOperation(value = "通过短链接获取长链接", notes = "通过短链接获取长链接", response = String.class)

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )

    @RequestMapping(value = "/tolong/{linkstr}", method = RequestMethod.GET)
    public Map<String, Object> toLong(@PathVariable String linkstr){

        // 通过短链接获取长链接
        String str =  lMap.get(linkstr);

        // 构造JSON输出
        Map<String, Object> map = new HashMap<>();
        map.put("url", linkstr);
        map.put("long", str);

        return map;
    }
}

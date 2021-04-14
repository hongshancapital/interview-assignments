package com.alice.shortdomain.controller;

import com.alice.shortdomain.dto.RequestDTO;
import com.alice.shortdomain.dto.ResponseDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 长域名转短域名，根据短域名查询长域名
 *
 * @author Alice [l1360737271@qq.com]
 * @date 2021/4/13 17:35
 */
@Controller
@Api(tags = "域名处理模块")
@RequestMapping("domain")
public class DomainTransController {


    /**
     * @return
     */
    @PostMapping("encode")
    @ApiOperation(value = "长域名转短域名")
    public ResponseDTO<String> encode(RequestDTO request) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        return responseDTO;
    }

    /**
     * @return
     */
    @PostMapping("decode")
    @ApiOperation(value = "根据短域名查询长域名")
    public ResponseDTO<String> decode(RequestDTO request) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        return responseDTO;
    }

}

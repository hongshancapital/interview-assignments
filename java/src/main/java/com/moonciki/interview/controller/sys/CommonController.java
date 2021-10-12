package com.moonciki.interview.controller.sys;

import com.moonciki.interview.commons.base.BaseController;
import com.moonciki.interview.commons.model.ResponseContent;
import com.moonciki.interview.service.sys.SystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "000.系统基础接口类")
@RestController
@RequestMapping("/sys")
public class CommonController extends BaseController {

    @Autowired
    SystemService systemService;

    @ApiOperation(value = "hello world")
    @GetMapping("/hello")
    public ResponseContent helloWorld() {

        log.info("testHello @@@@@@@@@@@");
        systemService.helloWorld();

        ResponseContent resp = ResponseContent.successResponse();

        return resp;
    }

}

package com.interview.shorter.controller;

import com.interview.shorter.service.Shorter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Bai Lijun mailTo: 13910160159@163.com
 * Created at 2021-04-23
 */

@RestController
@Api(tags = "url操作接口")
public class ShortController {
    @Autowired
    Shorter shorter;
    @GetMapping("/shorter")
    @ApiOperation("原始url转化为短码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "source", value = "原始的url",required = true)
    }
    )
    public String shorter(String source){
      String rlt =  shorter.shorting(0,source);
      return rlt;
    }
    @ApiOperation("短码置换原始url")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shortCode" ,value = "短码",required = true)
    }
    )
    @GetMapping("/get")
    public String get(String shortCode){
      return   shorter.restore(shortCode).getContent();
    }
}

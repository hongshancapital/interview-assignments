package com.interview.shorter.controller;

import com.interview.shorter.commons.Result;
import com.interview.shorter.service.Shorter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
            @ApiImplicitParam(name = "source", value = "原始的url", required = true)
    }
    )
    public Result shorter(String source) {
        Result r = checkParam(source);
        if (null != r) {
            return r;
        }
        String rlt = shorter.shorting(0, source);
        r = new Result();
        r.setCode(-1);
        r.setMessage("ok");
        r.setData(rlt);
        return r;
    }

    private Result checkParam(String source) {
        if (StringUtils.isEmpty(source)) {
            Result result = new Result();
            result.setCode(10001);
            result.setMessage("参数不能为空");
            return result;
        }
        return null;
    }

    @ApiOperation("短码置换原始url")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shortCode", value = "短码", required = true)
    }
    )
    @GetMapping("/get")
    public Result get(String shortCode) {
        Result r = checkParam(shortCode);
        if (null != r) {
            return r;
        }
        String rlt = shorter.restore(shortCode).getContent();
        r = new Result();
        r.setCode(-1);
        r.setMessage("ok");
        r.setData(rlt);
        return r;
    }
}

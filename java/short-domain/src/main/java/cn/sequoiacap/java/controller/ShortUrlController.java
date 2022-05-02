package cn.sequoiacap.java.controller;

import cn.sequoiacap.java.common.def.Constants;
import cn.sequoiacap.java.common.result.CommonResult;
import cn.sequoiacap.java.common.utils.AutoIncreUtils;
import cn.sequoiacap.java.common.utils.Base62Utils;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@Api(tags = "短域名服务")
@RequestMapping("/url")
public class ShortUrlController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShortUrlController.class);

    private static Map<String, String> urlMap = new HashMap<>();

    @RequestMapping(value = {"/put"}, method = RequestMethod.POST)
    @ApiOperation(value = "短域名存储接口", notes = "接受长域名信息，返回短域名信息。")
    @ApiImplicitParams({
            @ApiImplicitParam(name="longUrl",value="长域名信息", required=true, paramType="query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = Constants.SUCCESS_MSG),
            @ApiResponse(code = 500, message = Constants.INTERNAL_ERROR_MSG)
    })
    @ResponseBody
    public CommonResult putLongUrl(@RequestParam String longUrl) {
        LOGGER.info("putLongUrl==>longUrl:" + longUrl);
        long urlId = AutoIncreUtils.getNextUrlId();
        //最多保存1亿，防止内存溢出
        if (urlId > Constants.URL_MAX_COUNT) {
            LOGGER.info(Constants.OVER_FLOW_ERROR + "==>id:" + urlId);
            return CommonResult.failed(Constants.OVER_FLOW_ERROR);
        }
        String shortUrl = Base62Utils.encodeStr(urlId);
        LOGGER.info("putLongUrl==>shortUrl:" + shortUrl);
        urlMap.put(shortUrl, longUrl);

        return CommonResult.success(shortUrl);
    }

    @RequestMapping(value = {"/get/{shortUrl}"}, method = RequestMethod.GET)
    @ApiOperation(value = "短域名读取接口", notes = "接受短域名信息，返回长域名信息。")
    @ApiImplicitParams({
            @ApiImplicitParam(name="shortUrl",value="短域名信息", required=true, paramType="path")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = Constants.SUCCESS_MSG),
            @ApiResponse(code = 404, message = Constants.NOT_FOUND_MSG),
            @ApiResponse(code = 500, message = Constants.INTERNAL_ERROR_MSG)
    })
    @ResponseBody
    public CommonResult getLongUrl(@PathVariable String shortUrl) {
        LOGGER.info("getLongUrl==>shortUrl:" + shortUrl);
        String longUrl = urlMap.get(shortUrl);
        LOGGER.info("getLongUrl==>longUrl:" + longUrl);
        if (longUrl == null) {
            return CommonResult.notFound(Constants.NOT_FOUND_MSG);
        }
        return CommonResult.success(longUrl);
    }

}

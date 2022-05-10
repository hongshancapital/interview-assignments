package com.jingdata.controller;

import com.alibaba.fastjson.JSON;
import com.jingdata.constant.ConstPools;
import com.jingdata.exception.BizException;
import com.jingdata.exception.ExceptionCode;
import com.jingdata.params.common.CommonResponse;
import com.jingdata.params.read.ShortUrlReadRequest;
import com.jingdata.params.read.ShortUrlReadResponse;
import com.jingdata.params.write.ShortUrlWriteRequest;
import com.jingdata.params.write.ShortUrlWriteResponse;
import com.jingdata.service.ShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* 短地址控制器
*
* @Author wuyl
* @Date 2022/4/6 23:04:43
*/
@Api("短地址控制器")
@Slf4j
@RequestMapping("/")
@RestController
public class ShortUrlController {

    @Value("${jingdata.short.host}")
    private String host;

    @Value("${jingdata.error.page}")
    private String errorPage;

    @Resource
    private ShortUrlService shortUrlService;

    /**
     * 长地址转短地址
     * @param shortUrlWriteRequest
     * @return
     */
    @ApiOperation("长地址转短地址")
    @PostMapping("/short/write")
    public CommonResponse<?> write(
            @RequestBody ShortUrlWriteRequest shortUrlWriteRequest) {

        //TODO 鉴权，未要求

        log.info("短地址写入，入参:[{}]", JSON.toJSONString(shortUrlWriteRequest));
        //参数校验
        String longUrl = shortUrlWriteRequest.getLongUrl();
        if(StringUtils.isEmpty(longUrl)) {
            log.error("真实地址不能为空");
            return buildError(ExceptionCode.PARAMS_ERROR);
        }
        if(longUrl.length() > ConstPools.URL_LENGTH) {
            log.error("真实地址超出长度限制");
            return buildError(ExceptionCode.URL_LENGTH_OVER);
        }

        try {
            //写入
            String shortCode = shortUrlService.write(longUrl);
            String shortUrl = ConstPools.HTTP_PROTOCOL + host + ConstPools.SEPARATOR + shortCode;
            log.info("短地址写入，出参:[{}]", shortUrl);
            return buildWriteSuccess(shortUrl);
        } catch (BizException e) {
            log.error("短地址写入异常", e);
            return buildError(e);
        } catch (Exception e) {
            log.error("短地址写入异常", e);
            return buildError(ExceptionCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 短地址查询长地址
     * @param shortUrlReadRequest
     * @return
     */
    @ApiOperation("短地址查询长地址")
    @PostMapping("/short/read")
    public CommonResponse<?> read(
            @RequestBody ShortUrlReadRequest shortUrlReadRequest) {
        log.info("短地址读取，入参:[{}]", JSON.toJSONString(shortUrlReadRequest));
        //参数校验
        String shortUrl = shortUrlReadRequest.getShortUrl();
        if(StringUtils.isEmpty(shortUrl)) {
            log.error("短地址不能为空");
            return buildError(ExceptionCode.PARAMS_ERROR);
        }

        try {
            //读取
            String shortCode = shortUrl.replace(ConstPools.HTTP_PROTOCOL, ConstPools.BLANK_STR)
                    .replace(host, ConstPools.BLANK_STR)
                    .replace(ConstPools.SEPARATOR, ConstPools.BLANK_STR);
            String longUrl = shortUrlService.read(shortCode);
            log.info("短地址读取，出参:[{}]", longUrl);
            return buildReadSuccess(longUrl);
        } catch (BizException e) {
            log.error("短地址读取异常", e);
            return buildError(e);
        } catch (Exception e) {
            log.error("短地址读取异常", e);
            return buildError(ExceptionCode.UNKNOWN_ERROR);
        }
    }


    /**
     * 转发
     * @param shortCode 短码
     */
    @ApiOperation("转发")
    @GetMapping("/{shortCode}")
    public void mapping(
            @ApiParam(value = "短码", required = true) @PathVariable(value = "shortCode") String shortCode,
            HttpServletResponse response) throws IOException {
        log.info("短地址转发,shortCode=[{}]", shortCode);

        String longUrl = null;
        try {
            longUrl = shortUrlService.read(shortCode);
        } catch (BizException e) {
            e.printStackTrace();
            log.error("短地址转发异常,longUrl=[{}]", longUrl);
            //出现异常跳转错误页面
            response.sendRedirect(errorPage);
        }
        if(StringUtils.isEmpty(longUrl)) {
            longUrl = errorPage;
        }
        log.info("短地址转发,longUrl=[{}]", longUrl);
        response.sendRedirect(longUrl);
    }

    private CommonResponse<?> buildError(ExceptionCode exceptionCode) {
        return new CommonResponse<>(exceptionCode);
    }

    private CommonResponse<?> buildError(BizException e) {
        return new CommonResponse<>(e);
    }

    private CommonResponse<?> buildWriteSuccess(String shortUrl) {
        return new CommonResponse<>(ExceptionCode.SUCCESS, new ShortUrlWriteResponse(shortUrl));
    }

    private CommonResponse<?> buildReadSuccess(String longUrl) {
        return new CommonResponse<>(ExceptionCode.SUCCESS, new ShortUrlReadResponse(longUrl));
    }
}

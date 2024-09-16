package com.tb.link.app.web;

import com.tb.link.app.web.param.LongLinkParam;
import com.tb.link.app.web.param.ShorLinkParam;
import com.tb.link.app.web.view.ShortLinkOriginVO;
import com.tb.link.app.web.view.ShortLinkVO;
import com.tb.link.client.ShortLinkService;
import com.tb.link.client.domain.Result;
import com.tb.link.client.domain.enums.ShortLinkErrorCodeEnum;
import com.tb.link.client.domain.reponse.ShortLinkOriginResponse;
import com.tb.link.client.domain.reponse.ShortLinkResponse;
import com.tb.link.client.domain.request.AppName;
import com.tb.link.client.domain.request.LongLinkRequest;
import com.tb.link.client.domain.request.ShortLinkRequest;
import com.tb.link.infrastructure.constant.AppKeyConstant;
import com.tb.link.service.util.ExecuteFunUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author andy.lhc
 * @date 2022/4/17 10:00
 */
@RestController
@Api(value = "短域名服务", tags = {"短域名服务"})
@RequestMapping(value = "/shortlink", produces = "application/json")
@Slf4j
public class ShortLinkController {


    @Resource
    private ShortLinkService shortLinkService;


    @ApiOperation(value = "生成短链接")
    @RequestMapping(value = "/create", method = {RequestMethod.POST})
    @ApiResponses({@ApiResponse(code = 200, message = "OK", response = Result.class)})
    @ResponseBody
    public Result<ShortLinkVO> createShortLink(HttpServletRequest request
            , @RequestBody LongLinkParam param) {
        if (Objects.isNull(param)) {
            return Result.fail(ShortLinkErrorCodeEnum.PARAM_IS_NOT_NULL);
        }

        return ExecuteFunUtil.execute(param, "ShortLinkController.createShortLink", () -> {
            AppName appName = AppName.builder()
                    .appKey(StringUtils.isBlank(param.getAppKey()) ? AppKeyConstant.APP_KEY_COMMON : param.getAppKey())
                    .build();
            LongLinkRequest linkRequest = LongLinkRequest.builder()
                    .appName(appName)
                    .originLink(param.getOriginLink())
                    .expireTime(param.getExpireTime())
                    .build();
            Result<ShortLinkResponse> result = shortLinkService.generalShortLink(linkRequest);
            return convertShortLinkVO(result);
        });

    }

    private Result<ShortLinkVO> convertShortLinkVO(Result<ShortLinkResponse> result) {
        if (result.isSuccess()) {
            ShortLinkVO linkVO = ShortLinkVO.builder()
                    .shortLink(result.getData().getShortLink())
                    .shortLinkKey(result.getData().getShortLinkKey())
                    .startTime(
                            DateFormatUtils.format(result.getData().getStartTime()
                                    , "yyyy-MM-dd HH:mm:ss")
                    ).endTime(
                            DateFormatUtils.format(result.getData().getEndTime()
                                    , "yyyy-MM-dd HH:mm:ss")
                    ).build();
            return Result.success(linkVO);
        }
        return Result.fail(result.getErrorCode(), result.getErrorMsg());
    }

    @ApiOperation(value = "查询长链接")
    @ApiResponses({@ApiResponse(code = 200, message = "OK", response = Result.class)})
    @PostMapping(value = "/recover")
    @ResponseBody
    public Result<ShortLinkOriginVO> recoverShortLink(HttpServletRequest request
            , @RequestBody ShorLinkParam param) {
        if (Objects.isNull(param)
                || StringUtils.isBlank(param.getShortLink())) {
            return Result.fail(ShortLinkErrorCodeEnum.PARAM_IS_NOT_NULL);
        }

        return ExecuteFunUtil.execute(param, "ShortLinkController.recoverShortLink", () -> {
            AppName appName = AppName.builder()
                    .appKey(StringUtils.isBlank(param.getAppKey()) ? AppKeyConstant.APP_KEY_COMMON : param.getAppKey())
                    .build();
            ShortLinkRequest linkRequest = ShortLinkRequest.builder()
                    .appName(appName)
                    .shortLink(param.getShortLink())
                    .build();
            Result<ShortLinkOriginResponse> result = shortLinkService.recoverShortLink(linkRequest);
            return convertShortLinkOriginVO(result);
        });
    }

    private Result<ShortLinkOriginVO> convertShortLinkOriginVO(Result<ShortLinkOriginResponse> result) {
        if (result.isSuccess()) {
            ShortLinkOriginVO linkOriginVO = ShortLinkOriginVO.builder()
                    .originLink(result.getData().getOriginLink())
                    .build();
            return Result.success(linkOriginVO);
        }
        return Result.fail(result.getErrorCode(), result.getErrorMsg());
    }


    /*
    @ApiOperation(value = "短链转长链-压测使用")
    @ApiResponses({@ApiResponse(code = 200, message = "OK", response = Result.class)})
    @PostMapping(value = "/recoverPersist")
    @ResponseBody
    public Result<List<ShortLinkOriginVO>> persistRecoverShortLinks(HttpServletRequest request
            , @RequestBody List<ShorLinkParam> param) {
        List<ShortLinkOriginVO> result =  param.stream()
                .map(shorLinkParam -> recoverShortLink(request, shorLinkParam).getData()
                ).collect(Collectors.toList());
        return Result.success(result);
    }

    @ApiOperation(value = "生成短链-压测使用")
    @RequestMapping(value = "/createPersist", method = {RequestMethod.POST})
    @ApiResponses({@ApiResponse(code = 200, message = "OK", response = Result.class)})
    @ResponseBody
    public Result<List<ShortLinkVO>> persistCreateShortLink(HttpServletRequest request
            , @RequestBody List<LongLinkParam> param) {
        List<ShortLinkVO> result = param.stream()
                .map(longLinkParam -> createShortLink(request,longLinkParam).getData())
                .collect(Collectors.toList());
        return Result.success(result);
    }
     */

    @GetMapping(value = "/test")
    @ResponseBody
    public ResponseEntity test() {
        log.info("info~~~~~~~~~~~~~~~~test~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        ResponseEntity entity = ResponseEntity.ok("ok");
        return entity;
    }


}

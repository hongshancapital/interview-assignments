package com.sequoia.app;

import com.sequoia.domain.UrlRequest;
import com.sequoia.infrastructure.common.ApiResult;
import com.sequoia.infrastructure.common.StatusCodeEnum;
import com.sequoia.infrastructure.util.Constant;
import com.sequoia.service.ITinyUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * TinyUrlController
 *
 * @author KVLT
 * @date 2022-03-30.
 */
@Slf4j
@RestController
@Api("TinyUrlController相关的api")
public class TinyUrlController {

    @Resource
    private ITinyUrlService tinyUrlService;

    @Value("${tinyurl.prefix}")
    private String tinyUrlPrefix;

    @Value("${tinyurl.future.timeoutMills}")
    private Integer futureTimeoutMills = 1000;

    @ApiOperation(value = "根据长链接获取短链接")
    @PostMapping("/tinyUrl")
    public ApiResult<String> getTinyUrl(@RequestBody UrlRequest urlRequest) {
        if (null == urlRequest || StringUtils.isEmpty(urlRequest.getUrl())) {
            return ApiResult.error(StatusCodeEnum.PARAM_ERROR, "必须指定长链接地址");
        }

        // TODO 长链接的有效性校验 待完善，放到风控环节（防刷限流）处理，此处默认传入的长链接即为有效链接
        // 常规场景: 可以在业务上加前置校验规则，流转到该接口时已经是 合法长链接
        // 非法场景：通过 防刷限流 模块进行控制

        try {
            return tinyUrlService.getTinyUrlFuture(urlRequest.getUrl())
                    .thenApply(tinyCode -> ApiResult.ok(tinyUrlPrefix + tinyCode))
                    .get(futureTimeoutMills, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            log.error("getTinyUrl请求超时, {}", urlRequest, e);
            return ApiResult.error(StatusCodeEnum.SERVE_TIMEOUT);
        } catch (InterruptedException | ExecutionException e) {
            log.error("getTinyUrl请求异常, {}", urlRequest, e);
            return ApiResult.error(StatusCodeEnum.SERVE_ERROR);
        }
    }

    private boolean isValidTinyCode(String tinyCode) {
        int tinyCodeLength = StringUtils.length(tinyCode);
        return tinyCodeLength > 0 && tinyCodeLength <= Constant.TINYURL_MAX_LENGTH
                && StringUtils.containsOnly(tinyCode, Constant.DIGITS_CHAR_ARR);
    }

    @ApiOperation(value = "根据短链接获取长链接")
    @PostMapping("/originUrl")
    public ApiResult<String> getOriginUrl(@RequestBody UrlRequest urlRequest) {
        if (null == urlRequest || StringUtils.isEmpty(urlRequest.getUrl())) {
            return ApiResult.error(StatusCodeEnum.PARAM_ERROR, "必须指定短链接地址");
        }

        String tinyCode = StringUtils.removeStart(urlRequest.getUrl(), tinyUrlPrefix);
        if (!isValidTinyCode(tinyCode)) {
            return ApiResult.error(StatusCodeEnum.PARAM_ERROR,
                    "短链接不合格, 前缀必须是: " + tinyUrlPrefix
                            + "短码[" + tinyCode + "]字符范围要在[0-9][a-z][A-Z]内 & 长度要小于等于" + Constant.TINYURL_MAX_LENGTH);
        }

        return ApiResult.ok(tinyUrlService.getOriginUrl(tinyCode));
    }

    /**
     * 空方法，用来验证 机器 容器极限
     * @return null
     */
    @PostMapping("/test")
    public String test() {
        return null;
    }

}

package com.sequoia.app;

import com.sequoia.domain.UrlRequest;
import com.sequoia.infrastructure.common.ApiResult;
import com.sequoia.infrastructure.common.StatusCodeEnum;
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
            return ApiResult.error(StatusCodeEnum.PARAM_ERROR);
        }

//        return ApiResult.ok("test");
        try {
            return tinyUrlService.getTinyUrl(urlRequest.getUrl())
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

    @ApiOperation(value = "根据短链接获取长链接")
    @PostMapping("/originUrl")
    public ApiResult<String> getOriginUrl(@RequestBody UrlRequest urlRequest) {
        if (null == urlRequest || StringUtils.isEmpty(urlRequest.getUrl())) {
            return ApiResult.error(StatusCodeEnum.PARAM_ERROR);
        }

        try {
            String tinyCode = StringUtils.removeStart(urlRequest.getUrl(), tinyUrlPrefix);
            return tinyUrlService.getOriginUrl(tinyCode)
                    .thenApply(ApiResult::ok)
                    .get(futureTimeoutMills, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            log.error("getOriginUrl请求超时, {}", urlRequest, e);
            return ApiResult.error(StatusCodeEnum.SERVE_TIMEOUT);
        } catch (InterruptedException | ExecutionException e) {
            log.error("getOriginUrl请求异常, {}", urlRequest, e);
            return ApiResult.error(StatusCodeEnum.SERVE_ERROR);
        }
    }

    @PostMapping("/test")
    public String test() {
        return "test";
    }

}

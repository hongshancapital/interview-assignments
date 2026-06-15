package com.scdt.shortenurl.interfaces.rest;

import com.scdt.shortenurl.application.ShortenUrlBizService;
import com.scdt.shortenurl.common.Response;
import com.scdt.shortenurl.common.enums.ErrorCodeEnum;
import com.scdt.shortenurl.common.exception.BizException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description 短链转换服务接入层
 * @Author chenlipeng
 * @Date 2022/3/7 2:16 下午
 */
@Api("长短链转换服务")
@RestController
@RequestMapping("/shortenUrl")
public class ShortenUrlRestController {

    @Resource
    private ShortenUrlBizService shortenUrlBizService;

    /**
     * http://localhost:8080/swagger-ui.html
     * http://localhost:8080/shortenUrl?originalUrl=%22aa%22
     * https://blog.csdn.net/Hu531053/article/details/102807534
     *
     * @param originalUrl 长链地址
     * @return 短链地址
     */
    @ApiOperation(value = "根据长链地址生产短链地址", httpMethod = "GET")
    @GetMapping(value = "/genShortenUrl")
    public Response<String> genShortenUrl(
            @ApiParam(value = "长链地址") @RequestParam String originalUrl) {
        try {
            if (StringUtils.isEmpty(originalUrl)) {
                throw new BizException(ErrorCodeEnum.PARAM_IS_NULL);
            }
            String shortUrl = shortenUrlBizService.genShortenUrl(originalUrl);
            return Response.success(shortUrl);
        } catch (BizException e) {
            return Response.failure(e.getErrorCode());
        }
    }

    @ApiOperation(value = "根据短链地址获取长链地址", httpMethod = "GET")
    @GetMapping(value = "/getOriginalUrl")
    public Response<String> getOriginalUrl(
            @ApiParam(value = "短链地址") @RequestParam String shortenUrl) {
        try {
            if (StringUtils.isEmpty(shortenUrl)) {
                throw new BizException(ErrorCodeEnum.PARAM_IS_NULL);
            }
            String originalUrl = shortenUrlBizService.getOriginalUrl(shortenUrl);
            return Response.success(originalUrl);
        } catch (BizException e) {
            return Response.failure(e.getErrorCode());
        }

    }

}
